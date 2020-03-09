package nodes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NodeVisitor {


    public Integer visit(AST node) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class clazz=this.getClass();
        Method method=clazz.getMethod("visit_"+clazz.getSimpleName());
        return (Integer) method.invoke(node);


    }

}
