package fdc.repository.dao.common;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-27
 * Time: обнГ2:12
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface CommonMapper {

    @Select("select nvl(max(company_id)+1,'100001') from rs_fdccompany")
    String selectNewCompanyId();
}
