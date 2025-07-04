package com.github.semiprojectshop.repository.kyeongsoo.DAO;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrderQueryVO;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrderVO;
import com.github.semiprojectshop.repository.kyeongsoo.VO.OrdersProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderDAOImple implements OrderDAO{

    private final DataSource ds;
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

    // 주문 상세 정보 조회(이미지, 상품명, 가격 등)
    @Override
    public List<OrderVO> getOrderDetails(String userid) throws SQLException {

        List<OrderVO> orderVOList = new ArrayList<>();

        try {

            conn = ds.getConnection();

            String sql = " SELECT P.image_path, P.product_name, P.product_size, T.orders_id, p.product_id, to_char(O.created_at, 'yyyy-mm-dd') as created_at, O.status, " +
                    " T.at_price, T.at_discount_rate, T.review_id, T.QUANTITY " +
                    " FROM ( " +
                    "    SELECT image_path, product_name, product_size, P.product_id " +
                    "    FROM product P  " +
                    "    JOIN product_image I ON P.product_id = I.product_id " +
                    "    WHERE thumbnail = 1 " +
                    " ) P " +
                    " JOIN orders_product T ON P.product_id = T.product_id " +
                    " JOIN orders O ON O.orders_id = T.orders_id " +
                    " WHERE O.user_id = ? " +
                    " order by created_at desc ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            List<OrderQueryVO> orderQueryList = new ArrayList<>();

            while (rs.next()) {

                OrderQueryVO oqVO = new OrderQueryVO();
                oqVO.setImagePath(rs.getString("image_path"));
                oqVO.setProductName(rs.getString("product_name"));
                oqVO.setProductSize(rs.getString("product_size"));
                oqVO.setOrderId(rs.getInt("orders_id"));
                oqVO.setOrdersProductId(rs.getInt("product_id"));
                oqVO.setCreatedAt(rs.getString("created_at"));
                oqVO.setStatus(rs.getString("status"));
                oqVO.setAtPrice(rs.getInt("at_price"));
                oqVO.setAtDiscountRate(rs.getDouble("at_discount_rate"));
                oqVO.setReviewId(rs.getInt("review_id"));
                oqVO.setQuantity(rs.getInt("QUANTITY"));

                orderQueryList.add(oqVO);

            }

            Map<Integer, List<OrdersProductVO>> ordersProductMap = new HashMap<>();

            orderQueryList.forEach(orderQuery->{
                List<OrdersProductVO> or = ordersProductMap.get(orderQuery.getOrderId());

                OrdersProductVO ordersProductVO = new OrdersProductVO();

                ordersProductVO.setOrderProductId(orderQuery.getOrdersProductId());
                ordersProductVO.setThumbnailPath(orderQuery.getImagePath());
                ordersProductVO.setProductName(orderQuery.getProductName());
                ordersProductVO.setAtPrice(orderQuery.getAtPrice());
                ordersProductVO.setReviewId(orderQuery.getReviewId());
                ordersProductVO.setAtDiscountRate(orderQuery.getAtDiscountRate());
                ordersProductVO.setProductSize(orderQuery.getProductSize());
                ordersProductVO.setQuantity(orderQuery.getQuantity());

                if(or == null) {
                    // 처음들어온 주문값일땐 새로운 OrderVO 생성
                    OrderVO orderVO = new OrderVO();
                    orderVO.setOrderId(orderQuery.getOrderId());
                    orderVO.setStatus(orderQuery.getStatus());
                    orderVO.setCreatedAt(orderQuery.getCreatedAt());

                    // 주문별 상품 리스트 생성 및 set
                    List<OrdersProductVO> productList = new ArrayList<>();
                    productList.add(ordersProductVO);
                    orderVO.setOrdersProductList(productList);

                    orderVOList.add(orderVO);
                    ordersProductMap.put(orderQuery.getOrderId(), productList);
                }
                else {
                    or.add(ordersProductVO);
                }

            });

        } finally {
            close();
        }


        return orderVOList;
    }
}