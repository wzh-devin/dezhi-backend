package com.devin.dezhi.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 2025/4/22 21:16.
 *
 * <p>
 *     Redis工具类<br>
 *     使用StringRedisTemplate
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
// CHECKSTYLE:OFF
@Component
public class RedisUtil {
    private static StringRedisTemplate redisTemplate;

    static {
        RedisUtil.redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    }

    /** -------------------key相关操作--------------------- */

    /**
     * 删除key.
     *
     * @param key 缓存Key
     */
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key.
     *
     * @param keys 缓存Keys
     */
    public void delete(final Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key.
     *
     * @param key 缓存Key
     * @return 序列化的字节数组
     */
    public byte[] dump(final String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key.
     *
     * @param key 缓存Key
     * @return Boolean
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间.
     *
     * @param key 缓存Key
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return Boolean
     */
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间.
     *
     * @param key 缓存Key
     * @param date 过期日期
     * @return Boolean
     */
    public Boolean expireAt(final String key, final Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key.
     *
     * @param pattern 匹配规则
     * @return 匹配的 key 集合
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中.
     *
     * @param key 缓存Key
     * @param dbIndex 索引
     * @return Boolean
     */
    public Boolean move(final String key, final int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持.
     *
     * @param key 缓存Key
     * @return Boolean
     */
    public Boolean persist(final String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间.
     *
     * @param key 缓存Key
     * @param unit 时间单位
     * @return Long
     */
    public Long getExpire(final String key, final TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key.
     *
     * @return 随机Key
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称.
     *
     * @param oldKey 旧Key
     * @param newKey 新Key
     */
    public void rename(final String oldKey, final String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey.
     *
     * @param oldKey 旧Key
     * @param newKey 新Key
     * @return Boolean
     */
    public Boolean renameIfAbsent(final String oldKey, final String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型.
     *
     * @param key 缓存Key
     * @return DataType
     */
    public DataType type(final String key) {
        return redisTemplate.type(key);
    }

    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值.
     * @param key 缓存Key
     * @param value 值
     */
    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值.
     * @param key 缓存Key
     * @return String
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定 key 的值对象.
     * @param key 缓存Key
     * @param clazz 转换的对象
     * @param <T> 转换的对象泛型
     * @return T
     */
    public <T> T getObj(final String key, final Class<T> clazz) {
        return JSONUtil.toBean(get(key), clazz);
    }

    /**
     * 返回 key 中字符串值的子字符.
     * @param key 缓存Key
     * @param start 字符串的开始位置
     * @param end 字符串终止位置
     * @return String
     */
    public String getRange(final String key, final long start, final long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value).
     *
     * @param key 缓存Key
     * @param value 值
     * @return String
     */
    public String getAndSet(final String key, final String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit).
     *
     * @param key 缓存Key
     * @param offset 偏移量
     * @return Boolean
     */
    public Boolean getBit(final String key, final long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取.
     *
     * @param keys 缓存Keys
     * @return List
     */
    public List<String> multiGet(final Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value.
     *
     * @param key 缓存Key
     * @param offset 位置
     * @param value 值,true为1, false为0
     * @return Boolean
     */
    public Boolean setBit(final String key, final long offset, final boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout.
     *
     * @param key 缓存Key
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *            秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(final String key, final String value, final long timeout, final TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值.
     *
     * @param key 缓存Key
     * @param value 值
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始.
     *
     * @param key 缓存Key
     * @param value 值
     * @param offset 从指定位置开始覆写
     */
    public void setRange(final String key, final String value, final long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long size(final String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加.
     *
     * @param maps Map
     */
    public void multiSet(final Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在.
     *
     * @param maps Map
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean multiSetIfAbsent(final Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减.
     *
     * @param key 缓存Key
     * @param increment 自增的值
     * @return Long
     */
    public Long incrBy(final String key, final long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减.
     *
     * @param key 缓存Key
     * @param increment 自增的值
     * @return Double
     */
    public Double incrByFloat(final String key, final double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Integer
     */
    public Integer append(final String key, final String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值.
     *
     * @param key 缓存Key
     * @param field 字段
     * @return Object
     */
    public Object hGet(final String key, final String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值.
     *
     * @param key 缓存Key
     * @return Map
     */
    public Map<Object, Object> hGetAll(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值.
     *
     * @param key 缓存Key
     * @param fields 字段
     * @return List
     */
    public List<Object> hMultiGet(final String key, final Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 往Hash中存入数据.
     *
     * @param key 缓存Key
     * @param hashKey hashKey
     * @param value 值
     */
    public void hPut(final String key, final String hashKey, final String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 往Hash中存入多个数据.
     *
     * @param key 缓存Key
     * @param maps Map
     */
    public void hPutAll(final String key, final Map<String, String> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置.
     *
     * @param key 缓存Key
     * @param hashKey hashKey
     * @param value 值
     * @return Boolean
     */
    public Boolean hPutIfAbsent(final String key, final String hashKey, final String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段.
     *
     * @param key 缓存Key
     * @param fields 字段
     * @return Long
     */
    public Long hDelete(final String key, final Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在.
     *
     * @param key 缓存Key
     * @param field 字段
     * @return Boolean
     */
    public Boolean hExists(final String key, final String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment.
     *
     * @param key 缓存Key
     * @param field 字段
     * @param increment 增量
     * @return Long
     */
    public Long hIncrBy(final String key, final Object field, final long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment.
     *
     * @param key 缓存Key
     * @param field 字段
     * @param increment 增量
     * @return Double
     */
    public Double hIncrByFloat(final String key, final Object field, final double increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 获取所有哈希表中的字段.
     *
     * @param key 缓存Key
     * @return Set
     */
    public Set<Object> hKeys(final String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long hSize(final String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值.
     *
     * @param key 缓存Key
     * @return List
     */
    public List<Object> hValues(final String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对.
     *
     * @param key 缓存Key
     * @param options 扫描选项
     * @return Cursor
     */
    public Cursor<Map.Entry<Object, Object>> hScan(final String key, final ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /** ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素.
     *
     * @param key 缓存Key
     * @param index 索引 index>=0, 0表示第一个元素, 1表示第二个元素
     * @return String
     */
    public String lIndex(final String key, final long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素.
     *
     * @param key 缓存Key
     * @param start 开始位置, 0是开始位置
     * @param end 结束位置, -1返回所有
     * @return List
     */
    public List<String> lRange(final String key, final long start, final long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在list头部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lLeftPushAll(final String key, final String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 存储在list头部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lLeftPushAll(final String key, final Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lLeftPushIfPresent(final String key, final String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加.
     *
     * @param key 缓存Key
     * @param pivot pivot
     * @param value 值
     * @return Long
     */
    public Long lLeftPush(final String key, final String pivot, final String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储在list头部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lLeftPush(final String key, final String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 存储在list尾部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lRightPushAll(final String key, final String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 存储在list尾部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lRightPushAll(final String key, final Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lRightPushIfPresent(final String key, final String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 存储在list尾部.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long lRightPush(final String key, final String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 在pivot元素的右边添加值.
     *
     * @param key 缓存Key
     * @param pivot pivot
     * @param value 值
     * @return Long
     */
    public Long lRightPush(final String key, final String pivot, final String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值.
     *
     * @param key 缓存Key
     * @param index 位置
     * @param value 值
     */
    public void lSet(final String key, final long index, final String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素.
     *
     * @param key 缓存Key
     * @return 删除的元素
     */
    public String lLeftPop(final String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止.
     *
     * @param key 缓存Key
     * @param timeout 等待时间
     * @param unit 时间单位
     * @return String
     */
    public String lBLeftPop(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素.
     *
     * @param key 缓存Key
     * @return 删除的元素
     */
    public String lRightPop(final String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止.
     *
     * @param key 缓存Key
     * @param timeout 等待时间
     * @param unit 时间单位
     * @return String
     */
    public String lBRightPop(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回.
     *
     * @param sourceKey 缓存Key
     * @param destinationKey 缓存Key
     * @return String
     */
    public String lRightPopAndLeftPush(final String sourceKey, final String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止.
     *
     * @param sourceKey 缓存Key
     * @param destinationKey 缓存Key
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return String
     */
    public String lBRightPopAndLeftPush(final String sourceKey, final String destinationKey,
                                        final long timeout, final TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素.
     *
     * @param key 缓存Key
     * @param index
     *            index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *            index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     * @return Long
     */
    public Long lRemove(final String key, final long index, final String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置
     */
    public void lTrim(final String key, final long start, final long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long lLen(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    /** --------------------set相关操作-------------------------- */

    /**
     * set添加元素.
     *
     * @param key 缓存Key
     * @param values 值
     * @return Long
     */
    public Long sAdd(final String key, final String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素.
     *
     * @param key 缓存Key
     * @param values 值
     * @return Long
     */
    public Long sRemove(final String key, final Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素.
     *
     * @param key 缓存Key
     * @return Long
     */
    public String sPop(final String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合.
     *
     * @param key 缓存Key
     * @param value 值
     * @param destKey destKey
     * @return Boolean
     */
    public Boolean sMove(final String key, final String value, final String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long sSize(final String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Boolean
     */
    public Boolean sIsMember(final String key, final Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集.
     *
     * @param key 缓存Key
     * @param otherKey otherKey
     * @return Long
     */
    public Set<String> sIntersect(final String key, final String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @return Long
     */
    public Set<String> sIntersect(final String key, final Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中.
     *
     * @param key 缓存Key
     * @param otherKey otherKey
     * @param destKey destKey
     * @return Long
     */
    public Long sIntersectAndStore(final String key, final String otherKey, final String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @param destKey destKey
     * @return Long
     */
    public Long sIntersectAndStore(final String key, final Collection<String> otherKeys,
                                   final String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @return Long
     */
    public Set<String> sUnion(final String key, final String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @return Long
     */
    public Set<String> sUnion(final String key, final Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中.
     *
     * @param key 缓存Key
     * @param otherKey otherKey
     * @param destKey destKey
     * @return Long
     */
    public Long sUnionAndStore(final String key, final String otherKey, final String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @param destKey destKey
     * @return Long
     */
    public Long sUnionAndStore(final String key, final Collection<String> otherKeys,
                               final String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集.
     *
     * @param key 缓存Key
     * @param otherKey otherKey
     * @return Long
     */
    public Set<String> sDifference(final String key, final String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @return Long
     */
    public Set<String> sDifference(final String key, final Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中.
     *
     * @param key 缓存Key
     * @param otherKey otherKey
     * @param destKey destKey
     * @return Long
     */
    public Long sDifference(final String key, final String otherKey, final String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中.
     *
     * @param key 缓存Key
     * @param otherKeys otherKeys
     * @param destKey destKey
     * @return Long
     */
    public Long sDifference(final String key, final Collection<String> otherKeys,
                            final String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取集合所有元素.
     *
     * @param key 缓存Key
     * @return Set
     */
    public Set<String> setMembers(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素.
     *
     * @param key 缓存Key
     * @return String
     */
    public String sRandomMember(final String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素.
     *
     * @param key 缓存Key
     * @param count 获取数量
     * @return List
     */
    public List<String> sRandomMembers(final String key, final long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的.
     *
     * @param key 缓存Key
     * @param count 获取数量
     * @return Set
     */
    public Set<String> sDistinctRandomMembers(final String key, final long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 迭代集合中的元素.
     *
     * @param key 缓存Key
     * @param options 扫描选项
     * @return Cursor
     */
    public Cursor<String> sScan(final String key, final ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /**------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列.
     *
     * @param key 缓存Key
     * @param value 值
     * @param score 分数
     * @return Boolean
     */
    public Boolean zAdd(final String key, final String value, final double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量添加元素.
     *
     * @param key 缓存Key
     * @param values 值
     * @return Long
     */
    public Long zAdd(final String key, final Set<ZSetOperations.TypedTuple<String>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * 删除元素.
     *
     * @param key 缓存Key
     * @param values 值
     * @return Long
     */
    public Long zRemove(final String key, final Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值.
     *
     * @param key 缓存Key
     * @param value 值
     * @param delta 值
     * @return Double
     */
    public Double zIncrementScore(final String key, final String value, final double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列.
     *
     * @param key 缓存Key
     * @param value 值
     * @return 0表示第一位
     */
    public Long zRank(final String key, final Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列.
     *
     * @param key 缓存Key
     * @param value 值
     * @return Long
     */
    public Long zReverseRank(final String key, final Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置, -1查询所有
     * @return Set
     */
    public Set<String> zRange(final String key, final long start, final long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(final String key, final long start,
                                                                   final long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素.
     *
     * @param key 缓存Key
     * @param min 最小值
     * @param max 最大值
     * @return Set
     */
    public Set<String> zRangeByScore(final String key, final double min, final double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序.
     *
     * @param key 缓存Key
     * @param min 最小值
     * @param max 最大值
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(final String key,
                                                                          final double min, final double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @param start 开始位置
     * @param end 结束位置
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(final String key,
                                                                          final double min, final double max, final long start, final long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置
     * @return Set
     */
    public Set<String> zReverseRange(final String key, final long start, final long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(final String key, final long start, final long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(
            final String key, final double min, final double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @return Set
     */
    public Set<String> zReverseRangeByScore(final String key, final double min,
                                            final double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @param start 开始位置
     * @param end 结束位置
     * @return Set
     */
    public Set<String> zReverseRangeByScore(final String key, final double min,
                                            final double max, final long start, final long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @return Long
     */
    public Long zCount(final String key, final double min, final double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long zSize(final String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小.
     *
     * @param key 缓存Key
     * @return Long
     */
    public Long zZCard(final String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值.
     *
     * @param key 缓存Key
     * @param value 分数
     * @return Double
     */
    public Double zScore(final String key, final Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员.
     *
     * @param key 缓存Key
     * @param start 开始位置
     * @param end 结束位置
     * @return Long
     */
    public Long zRemoveRange(final String key, final long start, final long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员.
     *
     * @param key 缓存Key
     * @param min min
     * @param max max
     * @return Long
     */
    public Long zRemoveRangeByScore(final String key, final double min, final double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中.
     *
     * @param key 缓存Key
     * @param otherKey 缓存Key
     * @param destKey 缓存Key
     * @return Long
     */
    public Long zUnionAndStore(final String key, final String otherKey, final String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 并集.
     *
     * @param key 缓存Key
     * @param otherKeys 缓存Key集合
     * @param destKey 缓存Key
     * @return Long
     */
    public Long zUnionAndStore(final String key, final Collection<String> otherKeys,
                               final String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集.
     *
     * @param key 缓存Key
     * @param otherKey 缓存Key集合
     * @param destKey 缓存Key
     * @return Long
     */
    public Long zIntersectAndStore(final String key, final String otherKey,
                                   final String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 交集.
     *
     * @param key 缓存Key
     * @param otherKeys 缓存Key集合
     * @param destKey 缓存Key
     * @return Long
     */
    public Long zIntersectAndStore(final String key, final Collection<String> otherKeys,
                                   final String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 迭代.
     *
     * @param key 缓存Key
     * @param options 迭代参数
     * @return Cursor
     */
    public Cursor<ZSetOperations.TypedTuple<String>> zScan(final String key, final ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }
}
// CHECKSTYLE:ON
