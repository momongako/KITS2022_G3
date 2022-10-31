package com.example.projectemarketg3;

import com.example.projectemarketg3.entity.*;
import com.example.projectemarketg3.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class initData {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Random random;
    @Autowired
    private Slugify slugify;
    @Autowired
    private Faker faker;

    @Autowired
    private ImageRepository imageRepository;


    @Test
    void dataCategory() {
        for (int i = 0; i <8; i++) {
            Category category = Category.builder()
                    .name(faker.color().name())
                    .build();

            categoryRepository.save(category);
        }   }

    @Test
    void dataStatus() {
        for (int i = 0; i <8; i++) {
            Status category = Status.builder()
                    .name(faker.color().name())
                    .build();

            statusRepository.save(category);
        }   }

    @Test
    void dataSupplier() {
        for (int i = 0; i <8; i++) {
            Supplier category = Supplier.builder()
                    .name(faker.color().name())
                    .build();

            supplierRepository.save(category);
        }   }

    @Test
    void dataRanking() {
        for (int i = 0; i <8; i++) {
            Ranking category = Ranking.builder()
                    .name(faker.color().name())
                    .discount(faker.number().randomDigit())
                    .build();

            rankingRepository.save(category);
        }   }

    @Test
    void dataUser() {
        List<Ranking> rankings = rankingRepository.findAll();
        for (int i = 0; i < 20; i++) {
            int role = random.nextInt(2);
            List<String> rolesList = new ArrayList<>();
            rolesList.add("USER");

            if(role == 0) {
                rolesList.add("ADMIN");
            }

            Ranking ranking = rankings.get(random.nextInt(rankings.size()));

            User user = User.builder()
                    .name(faker.name().fullName())
                    .address(faker.address().streetAddress())
                    .email(faker.internet().emailAddress())
                    .gender(role==0?"Male":"FeMale")
                    .image(faker.company().logo())
                    .password(faker.internet().password())
                    .phone(faker.number().digits(9))
                    .role(rolesList)
                    .ranking(ranking)
                    .build();

            userRepository.save(user);
        }
    }

    @Test
    void dataProduct() {
        List<Category> categories = categoryRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();

        for (int i = 0; i < 30; i++) {
            Category category = categories.get(random.nextInt(categories.size()));
            Supplier supplier = suppliers.get(random.nextInt(suppliers.size()));

            Product product = Product.builder()
                    .description(faker.lorem().sentence())
                    .image(faker.company().logo())
                    .name(faker.name().fullName())
                    .origin(faker.toString())
                    .quantity(50)
                    .sold(2)
                    .category(category)
                    .supplier(supplier)
                    .build();

            productRepository.save(product);
        }
    }

    @Test
    void dataRating() {
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Product product = products.get(random.nextInt(products.size()));
            User user  = users.get(random.nextInt(users.size()));

            Rating rating = Rating.builder()
                    .image(faker.company().logo())
                    .note(faker.lorem().paragraph())
                    .star(5)
                    .product(product)
                    .user(user)
                    .checking(false)
                    .build();

            ratingRepository.save(rating);
        }
    }


    @Test
    void dataOrder() {
        List<Orders> orders = ordersRepository.findAll();
        List<Status> statuses = statusRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();

        for (int i = 0; i < 10 ; i++) {
            Set<OrderDetail> orderDetails = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                Product product = products.get(random.nextInt(products.size()));
                int num = random.nextInt(3) + 1;

                OrderDetail orderDetail = OrderDetail.builder()
                        .quantity(num)
                        .product(product)
                        .total((long) num*100000)
                        .build();

                orderDetails.add(orderDetail);
            }

            Status status = statuses.get(random.nextInt(statuses.size()));
            User user  = users.get(random.nextInt(users.size()));

            Orders order = Orders.builder()
                    .note(faker.lorem().paragraph())
                    .orderDetails(orderDetails)
                    .status(status)
                    .user(user)
                    .build();

            ordersRepository.save(order);
        }
    }

}
