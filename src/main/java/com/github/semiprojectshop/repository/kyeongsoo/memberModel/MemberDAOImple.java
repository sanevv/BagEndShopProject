package com.github.semiprojectshop.repository.kyeongsoo.memberModel;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.aery.util.security.Sha256;
import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemberDAOImple implements MemberDAO{

    private final DataSource ds;
    private final AES256 aes;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 사용한 자원을 반납하는 close() 메소드 생성하기
    private void close() {
        try {
            if(rs    != null) {rs.close();     rs=null;}
            if(pstmt != null) {pstmt.close(); pstmt=null;}
            if(conn  != null) {conn.close();  conn=null;}
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }// end of private void close()---------------


    @Override
    public MemberVO login(Map<String, String> paramap) throws SQLException {

        MemberVO member = null;

        try {
            conn = ds.getConnection();

            String sql = " select user_id, email, password, name, phone_number, zip_code, " +
                    "       address, address_details, to_char(register_at, 'yyyy-mm-dd') as register_at, role_id, status  " +
                    " from my_user " +
                    " where email = ? and password = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paramap.get("userEmail"));
            pstmt.setString(2, Sha256.encrypt(paramap.get("pwd")));

            rs = pstmt.executeQuery();

            if(rs.next()){

                member = new MemberVO();

                member.setUserId(rs.getInt("user_id"));
                member.setEmail(rs.getString("email"));
                member.setName(rs.getString("name"));
                member.setPhoneNumber(rs.getString("phone_number"));
                member.setZipCode(rs.getString("zip_code")); // getInt -> getString
                member.setAddress(rs.getString("address"));
                member.setAddressDetails(rs.getString("address_details"));
                member.setRegisterAt(rs.getString("register_at"));
                member.setRoleId(rs.getInt("role_id"));
                member.setStatus(rs.getString("status"));

            }


        } finally {
            close();
        }


        return member;
    }

    // 이메일 찾기를 위한 것
    @Override
    public boolean findPhoneNum(Map<String, String> paramap) throws SQLException {

        boolean result = false;

        String phoneNum = paramap.get("phoneNum");

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
            close();
        }

        return result;
    }

    // 아이디찾기에서 이메일과 날짜를 알려주는 것
    @Override
    public MemberVO knowTheEmailAndTheDate(Map<String, String> paramap) throws SQLException {
        MemberVO member = null;

        String phoneNum = paramap.get("phoneNum");

        try {
            conn = ds.getConnection();

            String sql = " select email, to_char(register_at, 'yyyy-mm-dd') as register_at  from my_user where phone_number=? and name=? ";


            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phoneNum);
            pstmt.setString(2, paramap.get("name"));

            rs = pstmt.executeQuery();


            if(rs.next()) {
                member = new MemberVO();

                member.setEmail(rs.getString("email"));
                member.setRegisterAt(rs.getString("register_at"));

                return member;
            }

        } finally {
            close();
        }

        return member;
    }

    // 비밀번호 찾기에서 이메일로 비밀번호를 찾는 것
    @Override
    public boolean findAPasswordByEmail(String email, String username, String userid) throws SQLException {

        boolean result = false;

        try {

            conn = ds.getConnection();

            String sql = " select email from my_user where email=? and name=? and email =? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, userid);
            rs = pstmt.executeQuery();

            result = rs.next();

        } finally {
            close();
        }

        return result;
    } // end of public boolean findAPasswordByEmail(String email, String username, String userid) throws SQLException

    // 휴대폰 번호로 비밀번호 찾기 여부를 판단하는 메서드
    @Override
    public boolean judgmentCalledMobilePhoneNumber(String phoneNumber) throws SQLException {

        boolean result = false;

        try {

            conn = ds.getConnection();

            String sql = " select phone_number from my_user where phone_number=? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            rs = pstmt.executeQuery();

            result = rs.next();


        } finally {
            close();
        }

        return result;
    }

    // 이메일로 비밀번호 변경하기
    @Override
    public int changePasswordByEmail(String email, String newPassword) throws SQLException {

        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " update my_user set password = ? where email = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Sha256.encrypt(newPassword));
            pstmt.setString(2, email);

            result = pstmt.executeUpdate();

        } finally {
            close();
        }

        return result;
    }

    // 휴대폰 번호로 비밀번호 변경하기
    @Override
    public int changePasswordByPhoneNumber(String phoneNumber, String newPassword) throws SQLException {

        int result = 0;

        try {
            conn = ds.getConnection();

            String sql = " update my_user set password = ? where phone_number = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Sha256.encrypt(newPassword));
            pstmt.setString(2, phoneNumber);

            result = pstmt.executeUpdate();

        } finally {
            close();
        }

        return result;
    }

    // 로그인한 회원이 내 정보 수정하기
    @Override
    public int memberOneChange(Map<String, String> paramap) throws SQLException {

        int n = 0;

        try {

            conn = ds.getConnection();

            String sql = " update my_user set password = ?, name = ?, phone_number = ?, zip_code = ?, " +
                    " address = ?, address_details = ? where email = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Sha256.encrypt(paramap.get("password")));
            pstmt.setString(2, paramap.get("name"));
            pstmt.setString(3, paramap.get("phoneNumber"));
            pstmt.setString(4, paramap.get("zipCode"));
            pstmt.setString(5, paramap.get("address"));
            pstmt.setString(6, paramap.get("addressDetails"));
            pstmt.setString(7, paramap.get("email"));

            n = pstmt.executeUpdate();


        } finally {
            close();
        }

        return n;
    }

    
    // 회원 탈퇴하기시 회원 상태 탈퇴로 업데이트
    @Override
    public int withdrawMember(String email, String password) throws SQLException {

        int n = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            conn = ds.getConnection();

            String sql = "UPDATE my_user SET status = '탈퇴' WHERE email = ? AND password = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, Sha256.encrypt(password));
            
            n = pstmt.executeUpdate();

        } finally {
            close();
        }

        return n;
    }

}
