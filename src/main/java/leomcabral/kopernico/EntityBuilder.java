package leomcabral.kopernico;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leomcabrall
 */
public class EntityBuilder{
    
    public <T> T build(final Class<T> clazz, final Map<String, Object> params) {
        T obj = getInstance(clazz);
        
        mapToObj(params, clazz, obj);
        
        return obj;
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(EntityBuilder.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EntityBuilder.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return instance;
    }

    private <T> void mapToObj(Map<String, Object> params, Class<T> clazz, T obj) {
        final AttributeMapper<T> mapper = AttributeMapper.criarMapper(clazz);
        
        for (String param : params.keySet()) {
            mapper.map(param, params.get(param));
        }
    }

}
