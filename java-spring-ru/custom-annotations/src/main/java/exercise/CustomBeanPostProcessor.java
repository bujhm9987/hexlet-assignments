package exercise;

import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor{

    private Map<String, Class> annotations = new HashMap<>();
    private Map<String, String> loggerLevels = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String level = bean.getClass().getAnnotation(Inspect.class).level();

            annotations.put(beanName, bean.getClass());
            loggerLevels.put(beanName, level);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!annotations.containsKey(beanName)) {
            return bean;
        }
        var annotation = annotations.get(beanName);
        var loggerLevel = loggerLevels.get(beanName);

        var proxyInstance = Proxy.newProxyInstance(
                annotation.getClassLoader(),
                annotation.getInterfaces(),
                (proxy, method, args) -> {

                    String message = String.format(
                            "Was called method: %s() with arguments: %s",
                            method.getName(),
                            Arrays.toString(args)
                    );

                    if (loggerLevel.equals("info")) {
                        LOGGER.info(message);
                    } else {
                        LOGGER.debug(message);
                    }

                    return method.invoke(bean, args);
                }
        );

        return proxyInstance;
    }
}
// END
