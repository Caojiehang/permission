package com.jiehang.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jiehang.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @ClassName BeanValidator
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-09 19:50
 **/

public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * normal validate
     * @param t
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Map<String,String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t,groups);
        if(validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                errors.put(violation.getPropertyPath().toString(),violation.getMessage());
            }
            return errors;
        }

    }

    /**
     * Collections type value validate
     * @param collection
     * @return
     */
    public static Map<String,String>  validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do {
            if(!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.hasNext();
            errors = validate(object,new Class[0]);

        } while (errors.isEmpty());
        return errors;
    }

    /**
     * Generic method for validation
     * @param first
     * @param objects
     * @return
     */
    public static Map<String,String> validateObject(Object first, Object... objects) {
        if(objects != null && objects.length >0) {
           return  validateList(Lists.asList(first,objects));
        } else {
           return validate(first,new Class[0]);
        }
    }

    /**
     * check  params exception
     * @param object
     * @throws ParamException
     */
    public static void check(Object object) throws ParamException {
        Map<String,String> map = BeanValidator.validateObject(object);
        /**
         * equal map != null && map.setEntry().size()>0
         */
        if(MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }

    }
}
