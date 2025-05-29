package plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import plus.entity.PlusAccount;

import java.util.List;
import java.util.Map;

public interface PlusAccountMapper extends BaseMapper<PlusAccount> {

    @Select("SELECT * FROM tb_account WHERE id > 100 and user_name = #{admin} limit 10000")
    public List<Map<String,Object>> queryTop10000(@Param("admin") String admin);
}
