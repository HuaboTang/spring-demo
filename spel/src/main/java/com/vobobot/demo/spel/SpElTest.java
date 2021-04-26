package com.vobobot.demo.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Bobo
 * @date 4/23/21
 */
public class SpElTest {

    public static void main(String[] args) {
        final SpelExpressionParser parser = new SpelExpressionParser();
        final Expression exp = parser.parseExpression("T(com.vobobot.demo.spel.SpElTest.Md5Utils).md5(#bean.a + #bean.b)");
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setVariable("bean", new Bean().setA("a").setB("b"));
        final Object value = exp.getValue(standardEvaluationContext);
        System.out.println(value);
    }

    public static class Md5Utils {
        public static String md5(String str) {
            if (str == null || "".equals(str.trim())) {
                return str;
            }
            return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Bean {
        private String a;
        private String b;
    }
}
