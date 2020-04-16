package org.quarkus.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicInvocationHandler implements InvocationHandler{

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
    log.info("Invoked method: {}", arg1.getName());
		return 42;
  }
  public static void main(String[] args) {
    Map proxyInstance = (Map) Proxy.newProxyInstance(DynamicInvocationHandler.class.getClassLoader(),
     new Class[]{Map.class},
    new DynamicInvocationHandler() );
    proxyInstance.put("hello", "world");
    log.info(proxyInstance.get("hello").toString());
    log.info(proxyInstance.put("sda", "sda").toString());
  }

}