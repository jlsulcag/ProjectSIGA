
package org.siga.bl;

import java.util.List;
import org.siga.be.Moneda;
import org.siga.dao.MonedaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author JSULCAG
 */
@Service("monedaBl")
public class MonedaBl {
    
    @Autowired
    private MonedaDao monedaDao;
    
    public List<Moneda> listar(){
        return monedaDao.listar();
    }
}
