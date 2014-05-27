package leomcabral.kopernico;

import org.hamcrest.core.Is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leomcabrall
 */
public class AttributeMapperTest {

    /**
     * Test of map method, of class AttributeMapper.
     */
    @Test
    public void testMap() {
        AttributeMapper<Entidade> mapper = AttributeMapper.criarMapper(Entidade.class);
        
        mapper.map("nome", "Leonardo");
        mapper.map("idade", 31);
        
        Entidade ent = mapper.get();
        
        assertThat(ent.getNome(), Is.is("Leonardo"));
        assertThat(ent.getIdade(), Is.is(31));
    }
    
    @Test
    public void testMapHeranca() {
        AttributeMapper<Entidade2> mapper = AttributeMapper.criarMapper(Entidade2.class);
        mapper.map("nome", "Leonardo");
        mapper.map("idade", 31);
        mapper.map("outro", "outro");
        
        Entidade2 ent = mapper.get();
        
        assertThat(ent.getNome(), Is.is("Leonardo"));
        assertThat(ent.getIdade(), Is.is(31));
        assertThat(ent.getOutro(), Is.is("outro"));
    }
    
    
}
class Entidade {
    private String nome;
    private int idade;
    public Entidade() {}
    public String getNome() { return nome;}
    public int getIdade() {return idade;}
}

class Entidade2 extends Entidade {
    private String outro;
    public String getOutro() { return outro;}
}
