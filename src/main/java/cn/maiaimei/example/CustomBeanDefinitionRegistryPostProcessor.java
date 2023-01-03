package cn.maiaimei.example;

import cn.maiaimei.example.annotation.SwiftHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 如果在这里执行 configurableListableBeanFactory.getBean() ，get到的bean属性无法注入
        // 因为 AbstractAutowireCapableBeanFactory#populateBean 方法的 getBeanPostProcessorCache().instantiationAware 会用到
        // org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
        // 但是此时 AutowiredAnnotationBeanPostProcessor 并不在 getBeanPostProcessorCache().instantiationAware 里！！！
        test(configurableListableBeanFactory);
    }

    /**
     * {@link AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)}
     */
    private void test(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        Map<String, Object> beans = configurableListableBeanFactory.getBeansWithAnnotation(SwiftHandler.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
