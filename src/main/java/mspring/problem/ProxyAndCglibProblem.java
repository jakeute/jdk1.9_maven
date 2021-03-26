package mspring.problem;

/**
 * spring aop 中有proxy与CGLIB两种技术 其中proxy只能对接口增强 cglib能对类增强
 * 代理类中 final方法为自身（getClass） 一般方法为被代理类  增强方法为 增强+被代理
 * aop：具体的流程
 *
 *  1.首先把所有的bean都注入容器中
 *  2.挑选出和切面表达式匹配 的 bean 生成代理类（spring的切面表达式是方法级别的）
 *  3.当一个bean没有实现接口的时候，会默认用cglib，当一个类有实现接口的时候，会用jdk动态代理; 当然你也可以选择配置强制使用cglib
 *  5.生成代理类重新注入容器
 *
 *  切面表达式切记是方法级别的 就算是父类的方法 bean也可以匹配
 *  代理类中只有与切面表达式匹配的方法才会被增强
 *
 *  需要注意的是 匹配规则相对复杂，而我们平时完全没必要细究，需要增强的地方逻辑比较简单，简单的匹配即可
 *  可以依靠idea的提示来协助 aop判断
 */
public class ProxyAndCglibProblem {
}
