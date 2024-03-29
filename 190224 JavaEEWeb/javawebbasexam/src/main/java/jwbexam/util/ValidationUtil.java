package jwbexam.util;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtil {
    private Validator validator;

    public ValidationUtil() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <M> boolean isValid(M model){
        return this.validator.validate(model).size() == 0;
    }
}
