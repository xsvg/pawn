package cn.cnplay.demo.annotation;

public @interface UserRole
{
	String[] value() default "";
}
