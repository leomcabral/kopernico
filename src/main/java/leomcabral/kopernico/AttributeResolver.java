package leomcabral.kopernico;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leomcabrall
 */
public class AttributeResolver {
    
    private Map<String, Field> campos;
    private Map<String, Method> metodos;
    
    public AttributeResolver(Class clazz) {
        campos = new HashMap<String, Field>();
        metodos = new HashMap<String, Method>();
        
        leAtributos(clazz);
        leMetodos(clazz);
        
    }

    public Field getAttributo(String nome) {
        return campos.get(nome);
    }
    
    private void leAtributos(Class clazz) {
        if (clazz.getSuperclass() != null) {
            leAtributos(clazz.getSuperclass());
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            campos.put(field.getName(), field);
        }
    }

    private void leMetodos(Class clazz) {
        if (clazz.getSuperclass() != null) {
            leMetodos(clazz.getSuperclass());
        }
        final Method[] metodos = clazz.getDeclaredMethods();
        for (int i = 0; i < metodos.length; i++) {
            Method method = metodos[i];
            this.metodos.put(method.getName(), method);
        }
    }
    
}
