package com.example.projectemarketg3.service;

import com.example.projectemarketg3.dto.DetailDto;
import com.example.projectemarketg3.dto.InfoUserShoppingDto;
import com.example.projectemarketg3.dto.RatingDto;
import com.example.projectemarketg3.dto.UserIdDto;
import com.example.projectemarketg3.entity.*;
import com.example.projectemarketg3.repository.*;
import com.example.projectemarketg3.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    public CartItem clickAddCart(@RequestBody DetailDto detailDto) {
        //lay ra san pham tu id
        Optional<Product> product = productRepository.findById(detailDto.getProductId());
        int quantityProduct = product.get().getQuantity();
//        lay ra user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.getUserById(detailDto.getUserId());


//        kiem tra xem trong gio hang da co san pham vua click add cart chua
        Optional<CartItem> optionalOrderDetail = Optional.ofNullable(cartItemRepository.findByProduct_IdAndUser_Id(detailDto.getProductId(), user.getId()));
// neu chua ton tai va so luong them vao gio phai be hon
        if (optionalOrderDetail.isEmpty() && detailDto.getQuantity() <= quantityProduct) {

            CartItem cartItem = CartItem.builder()
                    .product(product.get())
                    .quantity(detailDto.getQuantity())
                    .total(detailDto.getQuantity() * product.get().getPrice())
                    .user(user)
                    .productImage(product.get().getImage())
                    .build();


            return cartItemRepository.save(cartItem);
            // neu da ton tai va so luong them vao gio phai be hon
        } else if ((detailDto.getQuantity() + optionalOrderDetail.get().getQuantity()) <= quantityProduct) {
            optionalOrderDetail.get().setQuantity(detailDto.getQuantity() + optionalOrderDetail.get().getQuantity());
            optionalOrderDetail.get().setTotal((detailDto.getQuantity() + optionalOrderDetail.get().getQuantity()) * product.get().getPrice());
            return cartItemRepository.save(optionalOrderDetail.get());
        } else {
            return null;
        }
    }
// them san pham tu phuong thuc thanh toan off
    public OrderDetail clickAddDetailOff(@RequestBody DetailDto detailDto) {
        //lay ra san pham tu id
        Optional<Product> product = productRepository.findById(detailDto.getProductId());
        int quantityProduct = product.get().getQuantity();
        //        lay ra user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.getUserById(detailDto.getUserId());


//        kiem tra xem trong gio hang da co san pham vua click add cart chua
        Optional<OrderDetail> optionalOrderDetail = Optional.ofNullable(orderDetailRepository.findByProduct_IdAndUser_Id(detailDto.getProductId(), user.getId()));
// neu chua ton tai va so luong them vao gio phai be hon
        if (optionalOrderDetail.isEmpty() && detailDto.getQuantity() <= quantityProduct) {

            OrderDetail orderDetail = OrderDetail.builder() //create orderDetail ( product + quantity + total )
                    .product(product.get())
                    .quantity(detailDto.getQuantity())
                    .total(detailDto.getQuantity() * product.get().getPrice())
                    .user(user)
                    .build();

            return orderDetailRepository.save(orderDetail);
            // neu da ton tai va so luong them vao gio phai be hon
        } else if ((detailDto.getQuantity() + optionalOrderDetail.get().getQuantity()) <= quantityProduct) {
            optionalOrderDetail.get().setQuantity(detailDto.getQuantity() + optionalOrderDetail.get().getQuantity());
            optionalOrderDetail.get().setTotal((detailDto.getQuantity() + optionalOrderDetail.get().getQuantity()) * product.get().getPrice());
            return orderDetailRepository.save(optionalOrderDetail.get());
        } else {
            return null;
        }
    }

    @Transactional
    public Orders clickBuy(@RequestBody InfoUserShoppingDto info) {

        Optional<Status> status = statusRepository.findById(1L);

        //        lay ra user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.getUserById(info.getUserId());

        if(user.getRole().contains("ADMIN")){
            status = statusRepository.findById(4L);
        }


//        lay ra danh sach san pham hien co trong gio hang theo id khach
//        Set<OrderDetail> orderDetails = orderDetailRepository.findByUser_Id(user.get().getId());

        List<CartItem> cartItems = cartItemRepository.getByUser_Id(user.getId());
        Set<OrderDetail> orderDetails = new HashSet<>();

        for (var s : cartItems
        ) {
            orderDetails.add(OrderDetail.builder()
                    .user(s.getUser())
                    .total(s.getTotal())
                    .product(s.getProduct())
                    .quantity(s.getQuantity())
                    .build());

        }

//        Total
//        Variable used in lambda expression should be final or effectively final
        final Long[] total = {0L};
        orderDetails.forEach(s -> {
            total[0] += s.getTotal();
        });

//        chiet khau hoa don theo rank
        int disscount = user.getRanking() == null ? 0 : user.getRanking().getDiscount();
        if (disscount > 0) {
            total[0] = total[0] - ((total[0] * disscount) / 100);
        }

        Orders order = Orders.builder()
                .createAt(new Date(System.currentTimeMillis()))
                .note(info.getNote())
                .totalPrice(total[0])
                .orderDetails(orderDetails) //
                .status(status.get())
                .user(user) //
                .ship(20000)
                .disscount(disscount)
                .addressUser(info.getAddressUser() == null ? user.getAddress() : info.getAddressUser()) //
                .nameUser(info.getNameUser() == null ? user.getName() : info.getNameUser()) //
                .phoneUser(info.getPhoneUser() == null ? user.getPhone() : info.getPhoneUser()) //
                .point(info.getPoint() == null ? 0 : info.getPoint())
                .build();

        ordersRepository.save(order);

//        tru diem neu khach dung
        if (info.getPoint() > 0) {
            Double point = user.getPoint();
            user.setPoint(point - info.getPoint());
        }

//        tich diem cho hoa don tren 200k theo 1%
        Double userPoint = user.getPoint() == null ? 0 : user.getPoint();

        if (total[0] > 200000) {
            Long point = (total[0]) / 100;
            user.setPoint(userPoint + point);

//            cap nhap rank
            Long idRank = updateRank(userPoint + point);
            Optional<Ranking> ranking = rankingRepository.findById(idRank);

            user.setRanking(ranking.get());
            userRepository.save(user);

//            Bat dau tinh ngay tich diem
            if (user.getRank_date() == null) {
                user.setRank_date(new Date(System.currentTimeMillis()));
            }
        }

//        update lai so luong san pham va luot ban
        for (var s : orderDetails
        ) {
            Product product = productRepository.getProductById(s.getProduct().getId());
            product.setQuantity(Math.max(product.getQuantity() - s.getQuantity(), 0));
            product.setSold(product.getSold() + s.getQuantity());

            if(product.getQuantity() - s.getQuantity() <=0){
                product.setAvailable(false);
            }

            productRepository.save(product);
        }

        //        xoa cac detail khi khach click mua hang
//        orderDetailRepository.deleteAll(orderDetails);
        cartItemRepository.deleteAll(cartItems);
//        cartItemRepository.deleteAllCartItem();

        return order;
    }


    public Orders clickCancelOrder(Long id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        Optional<Status> status = statusRepository.findById(5L);
//        chuyen trang thai huy don
        orders.get().setStatus(status.get());
//        cap nhat lai so luong san pham va status sell
        Set<OrderDetail> orderDetails = orders.get().getOrderDetails();
        for (var s : orderDetails
        ) {
            Product product = s.getProduct();
            product.setSold(product.getSold() - s.getQuantity());
            product.setQuantity(product.getQuantity() + s.getQuantity());
            product.setAvailable(true);

            productRepository.save(product);
        }
//        Tru diem khach hang
        User user = orders.get().getUser();
        Double point = user.getPoint() -(orders.get().getTotalPrice()/100);
        user.setPoint(point);
        userRepository.save(user);

        return ordersRepository.save(orders.get());
    }

    public Long updateRank(Double point) {
        if (point < 100) {
            return 1L;
        } else if (point > 100 && point < 700) {
            return 2L;
        } else if (point > 700 && point < 7000) {
            return 3L;
        } else {
            return 4L;
        }
    }

    //  NEW DATA RATING ->  DANH GIA DON HANG CHECKING = 0;
    public Rating ratingProduct(RatingDto ratingDto) {

        Product product = productRepository.getProductById(ratingDto.getProductId());

//        lay ra user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.getUserById(ratingDto.getUserId());

        Rating rating = Rating.builder()
                .createAt(new Date(System.currentTimeMillis()))
                .product(product)
                .checking(false)
                .star(ratingDto.getStar())
                .image(ratingDto.getImage())
                .note(ratingDto.getNote())
                .user(user)
                .build();

        return ratingRepository.save(rating);
    }

    //    XAC NHAN DANH GIA CHECKING = 1 -> UPDATE AVG_RATING(product)

    public Rating updateCheckingProduct(Long id) {
//        cap nhat trang thai danh gia thanh public
        Optional<Rating> rating = ratingRepository.findById(id);
        rating.get().setChecking(true);
        ratingRepository.save(rating.get());

//        lay ra san pham cap nhat lai avg_rating
        Product product = rating.get().getProduct();
        Double ratingD = (rating.get().getStar() + product.getAvgRating()) / 2;
        product.setAvgRating(ratingD);
        productRepository.save(product);

        return rating.get();
    }
}
