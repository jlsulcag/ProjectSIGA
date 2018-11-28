
package org.siga;

import org.siga.bl.PedidoBl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author JSULCAG
 */
public class App {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        PedidoBl pedido = context.getBean(PedidoBl.class);
        
        
        System.out.println(pedido.listar(1));
    }
    
}
