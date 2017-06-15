package sample.classloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.MethodInfo;

/**
 * Created by jiangbenpeng on 08/06/2017.
 *
 * @author benpeng.jiang
 * @version 1.0.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("sample.classloader.Hello");

        insertLog(cc);


        Class c = cc.toClass();
        Hello h = (Hello) c.newInstance();
        h.say();
        h.laugh();
    }

    public static void insertLog(CtClass cc) throws Exception {
        CtMethod[] ctMethods = cc.getMethods();
        if (ctMethods != null) {
            for (int i = 0; i < ctMethods.length; i++) {
                CtMethod ctMethod = ctMethods[i];
                if (cc.equals(ctMethod.getDeclaringClass())) {
                    String methodName = ctMethod.getLongName();
                    ctMethod.insertBefore("{ System.out.println(\"" + methodName + ": before\"); }");
                    ctMethod.insertAfter("{ System.out.println(\"" + methodName + ": end\"); }");
                }
            }
        }

        cc.writeFile();
    }
}
