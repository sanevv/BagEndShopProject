package com.github.semiprojectshop.repository.kyeongsoo.memberModel;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemberDAOImple implements MemberDAO{

    private final DataSource ds;
    private final AES256 aes;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;


    @Override
    public MemberVO login(Map<String, String> paramap) throws SQLException {

        MemberVO member = null;

        try {
            conn = ds.getConnection();

            String sql = " select user_id, email, password, name, phone_number, zip_code, " +
                    "       address, address_details, to_char(register_at, 'yyyy-mm-dd') as register_at, role_id  " +
                    " from my_user " +
                    " where email = ? and password = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paramap.get("userEmail"));
            pstmt.setString(2, paramap.get("pwd"));

            rs = pstmt.executeQuery();

            if(rs.next()){

                member = new MemberVO();

                member.setUserId(rs.getInt("user_id"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));
                member.setPhoneNumber(rs.getString("phone_number"));
                member.setZipCode(rs.getInt("zip_code"));
                member.setAddress(rs.getString("address"));
                member.setAddressDetails(rs.getString("address_details"));
                member.setRegisterAt(rs.getString("register_at"));
                member.setRoleId(rs.getInt("role_id"));

                return member;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return member;
    }

    // 이메일 찾기를 위한 것
    @Override
    public boolean findPhoneNum(Map<String, String> paramap) throws SQLException {

        boolean result = false;

        String phoneNum = paramap.get("phoneNum");
        System.out.println("변환 전 전화번호: " + phoneNum);

        phoneNum = phoneNum.substring(0, 3) + "-" + phoneNum.substring(3,7) + "-" + phoneNum.substring(7);
        System.out.println("변환 후 전화번호: " + phoneNum);

        try {
            conn = ds.getConnection();

            String sql = " select phone_number from my_user where name=? and phone_number=? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paramap.get("name"));
            pstmt.setString(2, phoneNum);

            rs = pstmt.executeQuery();

            result = rs.next();

            System.out.println("조회 결과: " + result);

        } finally {

        }

        return result;
    }
}
























