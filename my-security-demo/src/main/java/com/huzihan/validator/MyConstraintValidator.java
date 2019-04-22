package com.huzihan.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//<MyConstraint,Object>:
//MyConstraint:实现哪个注解
//Object:这个注解可以放在哪个类型上面
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("initialize");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o);
        return false;
    }
}
