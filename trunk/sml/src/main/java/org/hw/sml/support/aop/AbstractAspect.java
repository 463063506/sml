package org.hw.sml.support.aop;

import org.hw.sml.support.LoggerHelper;


public abstract class AbstractAspect implements Aspect,Comparable<AbstractAspect>{
	/**
	 * 执行顺序
	 */
	private int orderId;
	/**
	 * 扫描 路径.类名.方法名
	 */
	private String packageMatchs;
	
	private String name="aspect-"+orderId;
	
	public boolean isPackageProxy(Object target){
		if(packageMatchs==null){
			return true;
		}
		String pacage=target.getClass().getName();
		for(String packageMatch:packageMatchs.split(" ")){
			try{
				if(pacage.matches(packageMatch.substring(0,packageMatch.lastIndexOf("."))))
					return true;
			}catch(Exception e){
			}
		}
		return false;
	}
	
	public boolean isProxy(Invocation invocation) throws Throwable {
		if(packageMatchs==null){
			return true;
		}
		String pacage=invocation.getTarget().getClass().getName()+"."+invocation.getMethod().getName();
		for(String packageMatch:packageMatchs.split(" ")){
			try{
				//System.out.println(pacage+":"+packageMatch+"--->"+pacage.matches(packageMatch));
				if(pacage.matches(packageMatch))
					return true;
			}catch(Exception e){
				LoggerHelper.getLogger().error(getClass(),e.getMessage());
			}
		}
		return false;
	}
	public  void doException(Invocation invocation) throws Throwable{
		
	}
	public String toString() {
		return "Aspect [orderId=" + orderId + ", name=" + name + "]";
	}

	public int compareTo(AbstractAspect o) {
		if(o.getOrderId()>this.orderId){
			return -1;
		}else
			return 1;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getPackageMatchs() {
		return packageMatchs;
	}

	public void setPackageMatchs(String packageMatchs) {
		this.packageMatchs = packageMatchs;
	}

}
