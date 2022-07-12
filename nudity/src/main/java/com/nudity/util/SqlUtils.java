package com.nudity.util;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/4 14:18
 */
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.ArrayUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

public class SqlUtils {

    /**
     * 获取aop中的SQL语句
     *
     * @param pjp
     * @param sqlSessionFactory
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> getMybatisSql(ProceedingJoinPoint pjp, SqlSessionFactory sqlSessionFactory) throws IllegalAccessException {
        //1.获取namespace+methodName
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String namespace = method.getDeclaringClass().getName();
        String methodName = method.getName();
        //2.根据namespace+methodName获取相对应的MappedStatement
        //MapperMethod.resolveMappedStatement
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Collection<String> mappedStatementNames = configuration.getMappedStatementNames();
        if(!mappedStatementNames.contains(namespace + "." + methodName))
        {
//            return ("该mapper方法未找到:"+namespace + "." + methodName);
            return new HashMap<>(0);
        }
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace + "." + methodName);
        Object[] objects = pjp.getArgs(); //获取实参
        //MapperMethod[61:].execute(SqlSession sqlSession, Object[] args)
        ParamNameResolver paramNameResolver = new ParamNameResolver(configuration, method);
        Object namedParams = paramNameResolver.getNamedParams(objects);
        //baseExecutor[134:].query
        BoundSql boundSql = mappedStatement.getBoundSql(namedParams);
        return showSql(configuration, boundSql);
    }


    /**
     * 解析BoundSql，生成不含占位符的SQL语句
     * 注释这段释放可以把入参写入sql，但是不方便查看
     * @param configuration
     * @param boundSql
     * @return
     */

    private static Map<String,Object> showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Map<String,Object> map = new HashMap<>(2);
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        map.put("sql",sql);
        if (parameterMappings.size() > 0 && parameterObject != null) {
            List<String> list = new ArrayList<>();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                list.add(getParameterValue(parameterObject));
//                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    String[] s = metaObject.getObjectWrapper().getGetterNames();
                    s.toString();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        list.add(getParameterValue(obj));
//                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        list.add(getParameterValue(obj));
//                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
            map.put("args",list);
        }
        return map;
    }

    /**
     * 若为字符串或者日期类型，则在参数两边添加''
     *
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = obj.toString() ;
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value =formatter.format(new Date()) ;
        } else {
            if (obj != null) {
                value = obj.toString();
            }
        }
        return value;
    }

//    private static String getParameterValue(Object obj) {
//        String value = null;
//        if (obj instanceof String) {
//            value = "'" + obj.toString() + "'";
//        } else if (obj instanceof Date) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "'" + formatter.format(new Date()) + "'";
//        } else {
//            if (obj != null) {
//                value = obj.toString();
//            } else {
//                value = "";
//            }
//        }
//        return value;
//    }


    /**
     * 获取myBaits输出的log
     * @param configuration
     * @param boundSql
     * @return
     */
    static String[] MybatisLog(Configuration configuration,BoundSql boundSql){
        String[] log = new String[2];
        List<Object> columnValues = getColumnValues(configuration, boundSql);
        log[0] = "Preparing: "+removeBreakingWhitespace(boundSql.getSql());
        log[1] = "Parameters: "+getParameterValueString(columnValues);
        return log;
    }

    private static String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        while (whitespaceStripper.hasMoreTokens()) {
            builder.append(whitespaceStripper.nextToken());
            builder.append(" ");
        }
        return builder.toString();
    }

    //MybatisDefaultParameterHandler.java [206:]
    static List<Object> getColumnValues(Configuration configuration, BoundSql boundSql){
        List<Object> columnValues = new ArrayList<>();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    try {
                        columnValues.add(value);
                    } catch (TypeException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    }
                }
            }
        }
        return columnValues;
    }

    protected static String getParameterValueString(List<Object> columnValues) {
        List<Object> typeList = new ArrayList<>(columnValues.size());
        for (Object value : columnValues) {
            if (value == null) {
                typeList.add("null");
            } else {
                typeList.add(objectValueString(value) + "(" + value.getClass().getSimpleName() + ")");
            }
        }
        final String parameters = typeList.toString();
        return parameters.substring(1, parameters.length() - 1);
    }

    protected static String objectValueString(Object value) {
        if (value instanceof Array) {
            try {
                return ArrayUtil.toString(((Array) value).getArray());
            } catch (SQLException e) {
                return value.toString();
            }
        }
        return value.toString();
    }
}

