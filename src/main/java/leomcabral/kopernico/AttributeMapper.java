package leomcabral.kopernico;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leomcabrall
 * @param <T> Tipo que será mapeado para uma instância.
 */
public class AttributeMapper<T> {
    
    private Class<T>  baseClass;
    private final AttributeResolver resolver;
    private T obj;
    
    private static final Map<Class<?>, Class<?>> PRIMITIVOS = new HashMap<Class<?>, Class<?>>();
    
    static {
        PRIMITIVOS.put(Boolean.TYPE, Boolean.class);
        PRIMITIVOS.put(Byte.TYPE, Byte.class);
        PRIMITIVOS.put(Character.TYPE, Character.class);
        PRIMITIVOS.put(Short.TYPE, Short.class);
        PRIMITIVOS.put(Integer.TYPE, Integer.class);
        PRIMITIVOS.put(Long.TYPE, Long.class);
        PRIMITIVOS.put(Double.TYPE, Double.class);
        PRIMITIVOS.put(Float.TYPE, Float.class);
        PRIMITIVOS.put(Void.TYPE, Void.TYPE);
    }
    
    private AttributeMapper(final AttributeResolver resolver, final Class<T> clazz) {
        this.resolver = resolver;
        this.baseClass = clazz;
    }
    
    public static <T> AttributeMapper<T> criarMapper(final Class<T> clazz) {
        final AttributeResolver _resolver = new AttributeResolver(clazz);
        return new AttributeMapper<T>(_resolver, clazz);
    }
    
    public void map(final String nomeAtributo, final Object value) {
        if (null == obj) {
            instanciarObjeto();
        }
        
        final Field field = resolver.getAttributo(nomeAtributo);
        if (null != field) {
            try {
                field.setAccessible(true);
                Object assertedValue = null;
                if (value != null && getBoxedType(field.getType()) == getBoxedType(value.getClass())) {
                    assertedValue = value;
                }
                field.set(obj, assertedValue);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Retorna o objeto mapeado.
     * @return 
     */
    public T get() {
        return this.obj;
    }

    private void instanciarObjeto() {
        try {
            obj = baseClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Não foi possível instanciar o objeto", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private Class<?> getBoxedType(Class<?> type) {
        Class<?> c = type;
        
        if (type.isPrimitive()) {
            c = PRIMITIVOS.get(type);
        }
        
        return c;
    }
}
