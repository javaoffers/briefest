//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.ibatis.binding;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaoffers.batis.modelhelper.aggent.MapperProxyAggentProcess;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class MapperMethodAggent {
  private final MapperMethod.SqlCommand command;
  private final MapperMethod.MethodSignature method;

  public MapperMethodAggent(Class<?> mapperInterface, Method method, Configuration config) {
    this.command = new MapperMethod.SqlCommand(config, mapperInterface, method);
    this.method = new MapperMethod.MethodSignature(config, mapperInterface, method);
  }

  public Object execute(SqlSession sqlSession, Object[] args) {
    Object param;
    Object result;
    switch(this.command.getType()) {
      case INSERT:
        param = this.method.convertArgsToSqlCommandParam(args);
        result = this.rowCountResult(sqlSession.insert(this.command.getName(), param));
        break;
      case UPDATE:
        param = this.method.convertArgsToSqlCommandParam(args);
        result = this.rowCountResult(sqlSession.update(this.command.getName(), param));
        break;
      case DELETE:
        param = this.method.convertArgsToSqlCommandParam(args);
        result = this.rowCountResult(sqlSession.delete(this.command.getName(), param));
        break;
      case SELECT:
        if(MapperProxyAggentProcess.isModel()){
          result = this.executeForMany(sqlSession, args);
        }else if (this.method.returnsVoid() && this.method.hasResultHandler()) {
          this.executeWithResultHandler(sqlSession, args);
          result = null;
        } else if (this.method.returnsMany()) {
          result = this.executeForMany(sqlSession, args);
        } else if (this.method.returnsMap()) {
          result = this.executeForMap(sqlSession, args);
        } else if (this.method.returnsCursor()) {
          result = this.executeForCursor(sqlSession, args);
        } else {
          param = this.method.convertArgsToSqlCommandParam(args);
          result = sqlSession.selectOne(this.command.getName(), param);
        }
        break;
      case FLUSH:
        result = sqlSession.flushStatements();
        break;
      default:
        throw new BindingException("Unknown execution method for: " + this.command.getName());
    }

    if (result == null && this.method.getReturnType().isPrimitive() && !this.method.returnsVoid()) {
      throw new BindingException("Mapper method '" + this.command.getName() + " attempted to return null from a method with a primitive return type (" + this.method.getReturnType() + ").");
    } else {
      return result;
    }
  }

  private Object rowCountResult(int rowCount) {
    Object result;
    if (this.method.returnsVoid()) {
      result = null;
    } else if (!Integer.class.equals(this.method.getReturnType()) && !Integer.TYPE.equals(this.method.getReturnType())) {
      if (!Long.class.equals(this.method.getReturnType()) && !Long.TYPE.equals(this.method.getReturnType())) {
        if (!Boolean.class.equals(this.method.getReturnType()) && !Boolean.TYPE.equals(this.method.getReturnType())) {
          throw new BindingException("Mapper method '" + this.command.getName() + "' has an unsupported return type: " + this.method.getReturnType());
        }

        result = rowCount > 0;
      } else {
        result = (long)rowCount;
      }
    } else {
      result = rowCount;
    }

    return result;
  }

  private void executeWithResultHandler(SqlSession sqlSession, Object[] args) {
    MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(this.command.getName());
    if (Void.TYPE.equals(((ResultMap)ms.getResultMaps().get(0)).getType())) {
      throw new BindingException("method " + this.command.getName() + " needs either a @ResultMap annotation, a @ResultType annotation," + " or a resultType attribute in XML so a ResultHandler can be used as a parameter.");
    } else {
      Object param = this.method.convertArgsToSqlCommandParam(args);
      if (this.method.hasRowBounds()) {
        RowBounds rowBounds = this.method.extractRowBounds(args);
        sqlSession.select(this.command.getName(), param, rowBounds, this.method.extractResultHandler(args));
      } else {
        sqlSession.select(this.command.getName(), param, this.method.extractResultHandler(args));
      }

    }
  }

  private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
    Object param = this.method.convertArgsToSqlCommandParam(args);
    List result;
    if (this.method.hasRowBounds()) {
      RowBounds rowBounds = this.method.extractRowBounds(args);
      result = sqlSession.selectList(this.command.getName(), param, rowBounds);
    } else {
      result = sqlSession.selectList(this.command.getName(), param);
    }

    if(MapperProxyAggentProcess.isModel()){
      return result;
    }

    if (!this.method.getReturnType().isAssignableFrom(result.getClass())) {
      return this.method.getReturnType().isArray() ? this.convertToArray(result) : this.convertToDeclaredCollection(sqlSession.getConfiguration(), result);
    } else {
      return result;
    }
  }

  private <T> Cursor<T> executeForCursor(SqlSession sqlSession, Object[] args) {
    Object param = this.method.convertArgsToSqlCommandParam(args);
    Cursor result;
    if (this.method.hasRowBounds()) {
      RowBounds rowBounds = this.method.extractRowBounds(args);
      result = sqlSession.selectCursor(this.command.getName(), param, rowBounds);
    } else {
      result = sqlSession.selectCursor(this.command.getName(), param);
    }

    return result;
  }

  private <E> Object convertToDeclaredCollection(Configuration config, List<E> list) {
    Object collection = config.getObjectFactory().create(this.method.getReturnType());
    MetaObject metaObject = config.newMetaObject(collection);
    metaObject.addAll(list);
    return collection;
  }

  private <E> E[] convertToArray(List<E> list) {
    E[] array = (E[]) Array.newInstance(this.method.getReturnType().getComponentType(), list.size());
    array = list.toArray(array);
    return array;
  }

  private <K, V> Map<K, V> executeForMap(SqlSession sqlSession, Object[] args) {
    Object param = this.method.convertArgsToSqlCommandParam(args);
    Map result;
    if (this.method.hasRowBounds()) {
      RowBounds rowBounds = this.method.extractRowBounds(args);
      result = sqlSession.selectMap(this.command.getName(), param, this.method.getMapKey(), rowBounds);
    } else {
      result = sqlSession.selectMap(this.command.getName(), param, this.method.getMapKey());
    }

    return result;
  }

  public static class MethodSignature {
    private final boolean returnsMany;
    private final boolean returnsMap;
    private final boolean returnsVoid;
    private final boolean returnsCursor;
    private final Class<?> returnType;
    private final String mapKey;
    private final Integer resultHandlerIndex;
    private final Integer rowBoundsIndex;
    private final ParamNameResolver paramNameResolver;

    public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
      Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
      if (resolvedReturnType instanceof Class) {
        this.returnType = (Class)resolvedReturnType;
      } else if (resolvedReturnType instanceof ParameterizedType) {
        this.returnType = (Class)((ParameterizedType)resolvedReturnType).getRawType();
      } else {
        this.returnType = method.getReturnType();
      }

      this.returnsVoid = Void.TYPE.equals(this.returnType);
      this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
      this.returnsCursor = Cursor.class.equals(this.returnType);
      this.mapKey = this.getMapKey(method);
      this.returnsMap = this.mapKey != null;
      this.rowBoundsIndex = this.getUniqueParamIndex(method, RowBounds.class);
      this.resultHandlerIndex = this.getUniqueParamIndex(method, ResultHandler.class);
      this.paramNameResolver = new ParamNameResolver(configuration, method);
    }

    public Object convertArgsToSqlCommandParam(Object[] args) {
      return this.paramNameResolver.getNamedParams(args);
    }

    public boolean hasRowBounds() {
      return this.rowBoundsIndex != null;
    }

    public RowBounds extractRowBounds(Object[] args) {
      return this.hasRowBounds() ? (RowBounds)args[this.rowBoundsIndex] : null;
    }

    public boolean hasResultHandler() {
      return this.resultHandlerIndex != null;
    }

    public ResultHandler extractResultHandler(Object[] args) {
      return this.hasResultHandler() ? (ResultHandler)args[this.resultHandlerIndex] : null;
    }

    public String getMapKey() {
      return this.mapKey;
    }

    public Class<?> getReturnType() {
      return this.returnType;
    }

    public boolean returnsMany() {
      return this.returnsMany;
    }

    public boolean returnsMap() {
      return this.returnsMap;
    }

    public boolean returnsVoid() {
      return this.returnsVoid;
    }

    public boolean returnsCursor() {
      return this.returnsCursor;
    }

    private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
      Integer index = null;
      Class<?>[] argTypes = method.getParameterTypes();

      for(int i = 0; i < argTypes.length; ++i) {
        if (paramType.isAssignableFrom(argTypes[i])) {
          if (index != null) {
            throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
          }

          index = i;
        }
      }

      return index;
    }

    private String getMapKey(Method method) {
      String mapKey = null;
      if (Map.class.isAssignableFrom(method.getReturnType())) {
        MapKey mapKeyAnnotation = (MapKey)method.getAnnotation(MapKey.class);
        if (mapKeyAnnotation != null) {
          mapKey = mapKeyAnnotation.value();
        }
      }

      return mapKey;
    }
  }

  public static class SqlCommand {
    private final String name;
    private final SqlCommandType type;

    public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
      String statementName = mapperInterface.getName() + "." + method.getName();
      MappedStatement ms = null;
      if (configuration.hasStatement(statementName)) {
        ms = configuration.getMappedStatement(statementName);
      } else if (!mapperInterface.equals(method.getDeclaringClass())) {
        String parentStatementName = method.getDeclaringClass().getName() + "." + method.getName();
        if (configuration.hasStatement(parentStatementName)) {
          ms = configuration.getMappedStatement(parentStatementName);
        }
      }

      if (ms == null) {
        if (method.getAnnotation(Flush.class) == null) {
          throw new BindingException("Invalid bound statement (not found): " + statementName);
        }

        this.name = null;
        this.type = SqlCommandType.FLUSH;
      } else {
        this.name = ms.getId();
        this.type = ms.getSqlCommandType();
        if (this.type == SqlCommandType.UNKNOWN) {
          throw new BindingException("Unknown execution method for: " + this.name);
        }
      }

    }

    public String getName() {
      return this.name;
    }

    public SqlCommandType getType() {
      return this.type;
    }
  }

  public static class ParamMap<V> extends HashMap<String, V> {
    private static final long serialVersionUID = -2212268410512043556L;

    public ParamMap() {
    }

    public V get(Object key) {
      if (!super.containsKey(key)) {
        throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + this.keySet());
      } else {
        return super.get(key);
      }
    }
  }
}
