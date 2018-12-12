package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.common.OrderType;
import alararestaurant.domain.dtos.ItemImportXmlDto;
import alararestaurant.domain.dtos.OrderImportDto;
import alararestaurant.domain.dtos.OrderImportRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String FILES_ORDERS_XML = System.getProperty("user.dir") + "/src/main/resources/files/orders.xml";

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(FILES_ORDERS_XML);
    }

    @Override
    public String importOrders() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();

        OrderImportRootDto orderImportRootDto = this.xmlParser
                .parseXml(OrderImportRootDto.class, FILES_ORDERS_XML);

        for (OrderImportDto orderImportDto : orderImportRootDto.getOrderImportDto()) {
            Employee employee = this.employeeRepository.findByName(orderImportDto.getEmployee()).orElse(null);

            if (employee == null) {
                continue;
            }

            boolean itemsExist = true;

            for (ItemImportXmlDto itemImportXmlDto : orderImportDto.getItemImportRootXmlDto().getItemImportXmlDtos()) {
                Item item = this.itemRepository.findByName(itemImportXmlDto.getName()).orElse(null);
                if (item == null) {
                    itemsExist = false;
                }
            }
            if (!itemsExist) {
                continue;
            }

            if (!this.validationUtil.isValid(orderImportDto)) {
                importResult.append(Constants.INVALID_DATA_FORMAT).append(System.lineSeparator());

                continue;
            }


            Order order = this.mapper.map(orderImportDto, Order.class);
            order.setEmployee(employee);

            List<OrderItem> orderItems = new ArrayList<>();

            Arrays.stream(orderImportDto.getItemImportRootXmlDto().getItemImportXmlDtos())
                    .forEach(itemImportXmlDto -> {
                        OrderItem orderItem = new OrderItem();

                        Item item = this.itemRepository.findByName(itemImportXmlDto.getName()).orElse(null);

                        orderItem.setQuantity(itemImportXmlDto.getQuantity());
                        orderItem.setItem(item);
                        orderItem.setOrder(order);

                        orderItems.add(orderItem);
                    });

            String date = orderImportDto.getDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

            order.setDateTime(localDateTime);
            order.setCustomer(orderImportDto.getName());

            order.setType(orderImportDto.getType());

            this.orderRepository.saveAndFlush(order);
            this.orderItemRepository.saveAll(orderItems);

            importResult.append(String.format(Constants.SUCCESSFULLY_IMPORTED_JSON,
                    order.getCustomer())).append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder result = new StringBuilder();

        List<Order> allBurgerFlippersOrders = this.orderRepository.findAllBurgerFlippersOrders();

        this.orderRepository.findAllBurgerFlippersOrders().forEach(order -> {
            result.append(String.format("Name: %s", order.getEmployee().getName())).append(System.lineSeparator());
            result.append("Orders:").append(System.lineSeparator());
            result.append(String.format(" Customer: %s", order.getCustomer())).append(System.lineSeparator());
            result.append(" Items:").append(System.lineSeparator());

            order.getOrderItems().forEach(orderItem -> {
                        result.append(String.format("  Name: %s%n", orderItem.getItem().getName()));
                        result.append(String.format("  Price: %.2f%n", orderItem.getItem().getPrice()));
                        result.append(String.format("  Quantity: %d%n", orderItem.getQuantity()));
                        result.append(System.lineSeparator());
                    }
            );
        });
        return result.toString();
    }
}
