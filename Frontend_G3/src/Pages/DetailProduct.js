import React, { useState, useEffect } from 'react';
import Categories from './HomeComponents/Categories';
import { Link } from "react-router-dom";
import Carousel from 'react-bootstrap/Carousel'
import 'bootstrap/dist/css/bootstrap.css';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import { useParams } from 'react-router-dom';
import { useCart } from 'react-use-cart';
import ProductService from '../services/ProductService';
import ReactStars from 'react-stars';

const DetailProduct = () => {
    const params = useParams('');
    const [product, setProduct] = useState([]);
    const [avgStarProduct, setAvgStarProduct] = useState(0);
    const [countReviewProduct, setCountReviewProduct] = useState(0);
    const [userRating, setUserRating] = useState([]);
    const [quantity, setQuantity] = useState(1)


    async function fetchData() {
        let res = await ProductService.getProduct(params.name, '', '', '', '')
        let data = res.data;
        setProduct(data)

    }
    const { addItem } = useCart();

    useEffect(() => {
        fetchData()

    }, [])


    useEffect(() => {
        let avg_product = 'http://localhost:8080/api/v1/rating/avg/' + params.name;
        fetch(avg_product)
            .then((response) => response.json())
            .then((data) => {
                setAvgStarProduct(data)
            })
    }, []);


    useEffect(() => {
        let count_review_product = 'http://localhost:8080/api/v1/rating/count/' + params.name;
        fetch(count_review_product)
            .then((response) => response.json())
            .then((data) => {
                setCountReviewProduct(data)
            })
    });

    useEffect(() => {
        let user_rating = 'http://localhost:8080/api/v1/rating/userrating/' + params.name;
        fetch(user_rating)
            .then((response) => response.json())
            .then((data) => {
                setUserRating(data)
            })
    }, []);


    if (product[0])
        return <>
            <div>
                {/* Product Details Section Begin */}
                <section className="product-details spad">
                    <div className="container">

                        {product[0] ?

                            <div className="row">
                                <div className="col-lg-6 col-md-6">
                                    <div className="product__details__pic">
                                        <div className="product__details__pic__item">
                                            {/* <img className="product__details__pic__item--large" src={require(`../img/product/details/product-details-1.jpg`)} alt="" /> */}
                                            <Carousel variant="dark">
                                                <Carousel.Item>
                                                    <img
                                                        className="d-block w-100"
                                                        // src={require(`../img/product/details/product-details-1.jpg`)}
                                                        src={product[0].image}
                                                        alt="First slide"
                                                    />
                                                    <Carousel.Caption>

                                                    </Carousel.Caption>
                                                </Carousel.Item>
                                                <Carousel.Item>
                                                    <img
                                                        className="d-block w-100"
                                                        src={require(`../img/product/details/product-details-2.jpg`)}
                                                        alt="Second slide"
                                                    />
                                                    <Carousel.Caption>

                                                    </Carousel.Caption>
                                                </Carousel.Item>
                                                <Carousel.Item>
                                                    <img
                                                        className="d-block w-100"
                                                        src={require(`../img/product/details/product-details-3.jpg`)}
                                                        alt="Third slide"
                                                    />
                                                    <Carousel.Caption>

                                                    </Carousel.Caption>
                                                </Carousel.Item>
                                            </Carousel>
                                        </div>

                                    </div>
                                </div>
                                <div className="col-lg-6 col-md-6">
                                    <div className="product__details__text">
                                        <h3>{product[0].name}</h3>
                                        <div className="product__details__rating">
                                            <ReactStars
                                                count={5}
                                                size={40}
                                                value={avgStarProduct}
                                                edit={false}
                                                activeColor="#ffd700"
                                            />
                                            <span>({countReviewProduct} reviews)</span>
                                        </div>
                                        <div className="product__details__price">{product[0].price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })} </div>
                                        <p>Bí xanh (bí đao) L1 WinEco là loại thực phẩm quen thuộc và phổ biến với người
                                            Việt Nam. Bí xanh có thể chế biến thành nhiều món ăn khác nhau như bí luộc,
                                            canh bí hầm xương</p>
                                        {/* data not ok */}
                                        <div className="input-group col-md-6 d-flex mb-3">
                                            <span className="input-group-btn mr-2">
                                                <button type="button" className="fa-solid fa-chevron-left"
                                                    data-type="minus"
                                                    data-field
                                                    onClick={() => quantity > 1 ? setQuantity(quantity - 1) : setQuantity(1)}>
                                                    <i className="ion-ios-remove" />
                                                </button>
                                            </span>
                                            <input type="text" id="quantity" name="quantity"
                                                className="form-control input-number col-md-5" defaultValue={1} value={quantity}
                                                onChange={(e) =>
                                                    setQuantity(eval(e.target.value) < product[0].quantity && eval(e.target.value) > 1
                                                        ? eval(e.target.value)
                                                        : eval(e.target.value) > product[0].quantity
                                                            ? product[0].quantity : 1)} />
                                            <span className="input-group-btn ml-2">
                                                <button type="button" className="fa-solid fa-angle-right"
                                                    data-type="plus"
                                                    data-fiel
                                                    onClick={() => setQuantity(quantity < product[0].quantity ? quantity + 1 : product[0].quantity)}>
                                                    <i className="ion-ios-add" />
                                                </button>
                                            </span>
                                        </div>
                                        <button id='button-add-new'
                                            style={{
                                                fontSize: '20px',
                                                border: '1px solid blue',
                                                borderRadius: '5px',
                                                padding: '5px'
                                            }}
                                            onClick={() => addItem(product[0], quantity)}>
                                            Thêm sản phẩm
                                        </button>



                                    </div>

                                </div>

                                <div className="col-lg-12">
                                    <div className="product__details__tab">
                                        <Tabs defaultActiveKey="second">
                                            <Tab eventKey="first" title="Mô tả">
                                                <h6>{product[0].name}</h6>
                                                <p>{product[0].description}</p>

                                            </Tab>
                                            <Tab eventKey="second" title="Setting">
                                                <h6>Products A</h6>
                                                <p>Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui.
                                                    Pellentesque in ipsum id orci porta dapibus. Proin eget tortor
                                                    risus. Vivamus
                                                    suscipit tortor eget felis porttitor volutpat. Vestibulum ac diam
                                                    sit amet quam
                                                    vehicula elementum sed sit amet dui. Donec rutrum congue leo eget
                                                    malesuada.
                                                    Vivamus suscipit tortor eget felis porttitor volutpat. Curabitur
                                                    arcu erat,
                                                    accumsan id imperdiet et, porttitor at sem. Praesent sapien massa,
                                                    convallis a
                                                    pellentesque nec, egestas non nisi. Vestibulum ac diam sit amet quam
                                                    vehicula
                                                    elementum sed sit amet dui. Vestibulum ante ipsum primis in faucibus
                                                    orci luctus
                                                    et ultrices posuere cubilia Curae; Donec velit neque, auctor sit
                                                    amet aliquam
                                                    vel, ullamcorper sit amet ligula. Proin eget tortor risus.</p>
                                                <p>Praesent sapien massa, convallis a pellentesque nec, egestas non
                                                    nisi. Lorem
                                                    ipsum dolor sit amet, consectetur adipiscing elit. Mauris blandit
                                                    aliquet
                                                    elit, eget tincidunt nibh pulvinar a. Cras ultricies ligula sed
                                                    magna dictum
                                                    porta. Cras ultricies ligula sed magna dictum porta. Sed porttitor
                                                    lectus
                                                    nibh. Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.
                                                    Vestibulum ac diam sit amet quam vehicula elementum sed sit amet
                                                    dui. Sed
                                                    porttitor lectus nibh. Vestibulum ac diam sit amet quam vehicula
                                                    elementum
                                                    sed sit amet dui. Proin eget tortor risus.</p>
                                            </Tab>
                                            <Tab eventKey="third" title="Aboutus">
                                                <h6>Products B</h6>
                                                <p>Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui.
                                                    Pellentesque in ipsum id orci porta dapibus. Proin eget tortor
                                                    risus. Vivamus
                                                    suscipit tortor eget felis porttitor volutpat. Vestibulum ac diam
                                                    sit amet quam
                                                    vehicula elementum sed sit amet dui. Donec rutrum congue leo eget
                                                    malesuada.
                                                    Vivamus suscipit tortor eget felis porttitor volutpat. Curabitur
                                                    arcu erat,
                                                    accumsan id imperdiet et, porttitor at sem. Praesent sapien massa,
                                                    convallis a
                                                    pellentesque nec, egestas non nisi. Vestibulum ac diam sit amet quam
                                                    vehicula
                                                    elementum sed sit amet dui. Vestibulum ante ipsum primis in faucibus
                                                    orci luctus
                                                    et ultrices posuere cubilia Curae; Donec velit neque, auctor sit
                                                    amet aliquam
                                                    vel, ullamcorper sit amet ligula. Proin eget tortor risus.</p>
                                                <p>Praesent sapien massa, convallis a pellentesque nec, egestas non
                                                    nisi. Lorem
                                                    ipsum dolor sit amet, consectetur adipiscing elit. Mauris blandit
                                                    aliquet
                                                    elit, eget tincidunt nibh pulvinar a. Cras ultricies ligula sed
                                                    magna dictum
                                                    porta. Cras ultricies ligula sed magna dictum porta. Sed porttitor
                                                    lectus
                                                    nibh. Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.
                                                    Vestibulum ac diam sit amet quam vehicula elementum sed sit amet
                                                    dui. Sed
                                                    porttitor lectus nibh. Vestibulum ac diam sit amet quam vehicula
                                                    elementum
                                                    sed sit amet dui. Proin eget tortor risus.</p>
                                            </Tab>
                                        </Tabs>

                                    </div>
                                </div>
                            </div>
                            : 'loading'
                        }

                    </div>
                </section>
                {/* Product Details Section End */}

                {/* top product */}

                <Categories />

                {/* end top product */}


            </div>
            <div>
                <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
                    rel="stylesheet" />
                <div className="container">
                    <div className="be-comment-block">
                        <h1 className="comments-title">Comments ({countReviewProduct})</h1>
                        {userRating.map((item, index) => <>

                            <div className="be-comment">
                                <div className="be-img-comment">
                                    <a href="blog-detail-2.html">
                                        <img src={item.user.image} alt=""
                                            className="be-ava-comment" />
                                    </a>
                                </div>

                                <div className="row">

                                    <span className="be-comment-name ms-2">
                                        {item.user.name}<ReactStars
                                            count={5}
                                            size={10}
                                            value={item.star}
                                            edit={false}
                                            activeColor="#ffd700"
                                        />

                                    </span>


                                    <span className="be-comment-time float-end">
                                        <i className="fa fa-clock-o" />
                                        {item.createAt}
                                    </span>

                                    <p className="be-comment-text col-12">
                                        {item.note}
                                    </p>
                                </div>

                            </div>
                        </>)}
                    </div>
                </div>
            </div>
        </>


};

export default DetailProduct;