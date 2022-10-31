package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.dto.RatingDto;
import com.example.projectemarketg3.entity.*;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.*;
import com.example.projectemarketg3.request.*;
import com.example.projectemarketg3.service.ShoppingService;
import com.example.projectemarketg3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AdminController {
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    //=========================== CATEGORY ===============================================
    // create a new category rest api
    @PostMapping("/api/admin/category")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // update category rest api
    @PutMapping("/api/admin/category/{id}")
    public ResponseEntity<Category> updatedCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("category not exist with id :" + id));

        category.setName(categoryDetails.getName());
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    //    delete by id
    @DeleteMapping("/api/admin/category/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("category not exist with id :" + id));
        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //================================= ORDER =====================================
    // lấy order theo id order
    @GetMapping("/api/admin/orders/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("order not exist with id :" + id));
        return ResponseEntity.ok(order);
    }

    // update order rest api
    @PutMapping("/api/admin/orders/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders orderDetails) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("order not found with id :" + id));

        order.setNote(orderDetails.getNote());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setOrderDetails(orderDetails.getOrderDetails());
        order.setStatus(orderDetails.getStatus());
        order.setUser(orderDetails.getUser());

        ordersRepository.save(order);
        return ResponseEntity.ok(order);
    }

    // delete order rest api
    @DeleteMapping("/api/admin/orders/{id}")
    public Orders deleteOrder(@PathVariable Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("order not exist with id :" + id));
        ordersRepository.delete(order);
        return order;
    }

//    ORDER OFF LINE
    @PostMapping("/api/admin/order-off")
    public Orders orderOffLine(@RequestBody OrderOffRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Orders orders = Orders.builder()

                .build();

        return orders;
    }

//======================= DETAILS ORDER ============================

    // delete order detail rest api
    @DeleteMapping("/api/admin/orders/details/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrderDetails(@PathVariable Long id) {
        OrderDetail orderdetails = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("orderdetails not exist with id :" + id));
        orderDetailRepository.delete(orderdetails);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

//    ============================== PRODUCT ===============================

    // create a new product rest api
    @PostMapping("/api/admin/product")
    public Product createProduct(@RequestBody ProductRequest request) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        Optional<Supplier> supplier = supplierRepository.findById(request.getSupplierId());

        Product product = Product.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .price(request.getPrice())
                .image(request.getImage())
                .sold(request.getSold())
                .origin(request.getOrigin())
                .description(request.getDescription())
                .slug(request.getSlug())
                .avgRating(0.0)
                .category(category.get())
                .supplier(supplier.get())
                .available(true)
                .build();
        return productRepository.save(product);
    }

    // delete product rest api
    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("product not exist with id :" + id));
        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //    nhap them hang
    @PutMapping("/api/admin/products/{id}")
    public Product updateQuantityProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        Product product = productRepository.getProductById(id);
        product.setQuantity(product.getQuantity() + quantity);
        product.setAvailable(true);
        return productRepository.save(product);
    }

    //====================================== RANK =========================================
    @PostMapping("/api/admin/rank")
    public Ranking createNewRanking(@RequestBody Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    @PutMapping("/api/admin/rank/{id}")
    public Ranking updateRankingById(@PathVariable Long id, @RequestParam Integer discount) {
        Optional<Ranking> rankingOptional = rankingRepository.findById(id);
        if (rankingOptional.isEmpty()) throw new RuntimeException("not found Ranking id = " + id);

        Ranking rankingNew = rankingOptional.get();
        rankingNew.setDiscount(discount);
        rankingRepository.save(rankingNew);
        return rankingNew;
    }

    //lấy ra danh sách khách có cùng rank theo tên rank
    @GetMapping("/api/admin/rank-users/{name}")
    public List<UserRequest> getAllUserByRank(@PathVariable String name) {
        return userService.findDistinctByRanking_NameOrderByRank_dateDesc(name);
    }

    //    ====================================== RATING =======================================
// create a new rating rest api
    @PostMapping("/api/user/rating")
    public Rating createRating(@RequestBody RatingDto ratingDto) {

        Optional<User> user = userRepository.findById(ratingDto.getUserId());
        Optional<Product> product = productRepository.findById(ratingDto.getProductId());

        Rating rating = Rating.builder()
                .createAt(ratingDto.getCreateAt())
                .note(ratingDto.getNote())
                .image(ratingDto.getImage())
                .star(ratingDto.getStar())
                .checking(false)
                .user(user.get())
                .product(product.get())
                .build();

        return ratingRepository.save(rating);
    }


    // xác nhận rating nếu là true cập nhật false và ngược lại
    @PutMapping("/api/admin/user-rating/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("rating not exist with id :" + id));

        rating.setCreateAt(new Date(System.currentTimeMillis()));
        rating.setChecking(!rating.getChecking());

        ratingRepository.save(rating);

        return ResponseEntity.ok(ratingRepository.save(rating));
    }

    // xóa rrating    @DeleteMapping("/api/admin/rating/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRating(@PathVariable Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("rating not exist with id :" + id));
        ratingRepository.delete(rating);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //    XAC NHAN DANH GIA CHECKING = true -> UPDATE AVG_RATING(product)
    @PutMapping("/api/admin/rating-product/{id}")
    public Rating updateCheckingProduct(@PathVariable Long id) {
        return shoppingService.updateCheckingProduct(id);
    }


    //    ========================= SUPPLIER ==========================================
    @PostMapping("/api/admin/supplier")
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // update supplier rest api
    @PutMapping("/api/admin/supplier/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestParam String name) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("supplier not exist with id :" + id));

        supplier.setName(name);

        supplierRepository.save(supplier);

        return ResponseEntity.ok(supplier);
    }

    // delete supplier rest api
    @DeleteMapping("/api/admin/supplier/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSupplier(@PathVariable Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("supplier not exist with id :" + id));
        supplierRepository.delete(supplier);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    //    ========================================= PURCHASES =================================
//    lay ra cac oder trong thang
    @GetMapping("/api/admin/purchases/{month}")
    public PurchasesRequest getOrdersByCreateAt_Month(@PathVariable Integer month) {

        List<Orders> orders = ordersRepository.findAll();

        List<Orders> ordersMonth = new ArrayList<>();
        List<Orders> orderCancel = new ArrayList<>();
        Set<Long> idUser = new HashSet<>();

        for (Orders o : orders
        ) {

//            Loc ra theo thang
            Date date = o.getCreateAt();
            if (date.getMonth() + 1 == month) {
                //            lay ra user
                idUser.add(o.getUser().getId());
//                lay ra don huy
                if (o.getStatus().getId() == 5) {
                    orderCancel.add(o);
//                    lay ra don hoan thanh
                } else {
                    ordersMonth.add(o);
                }
            }
        }

        PurchasesRequest request = new PurchasesRequest(ordersMonth.size(), orderCancel.size(), idUser.size());
        return request;
    }

    @GetMapping("/api/admin/orders-status/{id}")
    public List<Orders> getOrdersByCategory(@PathVariable Long id) {
        return ordersRepository.findByStatus_IdOrderByCreateAtDesc(id);
    }


//    ================================= Shopping ======================================================

    //    UPDATE STATUS ORDER
    @PutMapping("/api/admin/update-status-order")
    public String clickUpdateOrderStaus(@RequestBody StatusOrderRequest request) {
        Optional<Orders> orders = ordersRepository.findById(request.getOrderId());
        Optional<Status> status = statusRepository.findById(request.getStatusId());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (orders.get().getUserSucceed() == null || orders.get().getUserSucceed().getId().equals(user.getId())) {

            orders.get().setStatus(status.get());
            orders.get().setUserSucceed(user);
            ordersRepository.save(orders.get());
            return "Cap nhat trang thai don hang thanh cong ";
        } else {
            return "Don hang chi duoc xac nhan boi 1 Admin";
        }
    }

//    ===================================== USER =================================================

    @GetMapping("/api/admin/user")
    public List<User> getCustomer(@RequestParam Optional<String> name) {
        if (name.isPresent()) {
            List<User> getCustomer = userRepository.findByNameStartsWithIgnoreCaseOrderByNameAsc(name.get());
            return getCustomer.stream().filter(s -> !s.getRole().contains("ADMIN")).collect(Collectors.toList());
        } else {
            return userRepository.findAll();
        }
    }

    @GetMapping("/api/admin/user/{email}")
    public UserRequest infoUser(@PathVariable String email) {
        return userService.infoUserByEmail(email);
    }

    //Tìm kiếm theo name user
    @GetMapping("/api/admin/user-search")
    public List<User> findNameUser(@RequestParam("name") String name) {
        return userRepository.findByNameContainsIgnoreCaseOrderByRanking_IdDesc(name);
    }
}
