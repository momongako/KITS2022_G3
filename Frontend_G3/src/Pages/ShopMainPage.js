import React, { useEffect, useState } from 'react';
import { Link, useParams } from "react-router-dom";
import CategoriesService from '../services/CategoriesService';
import ProductService from '../services/ProductService';
import ReactPaginate from 'react-paginate';

// import Slider from '@mui/material/Slider';
import ReactStars from "react-rating-stars-component";

// import { Slider, RangeSlider } from 'rsuite';
import RangeSlider from 'rsuite/RangeSlider';
import './StyleShopMain/Slider.less';

export default function ShopMainPage() {
    const param = useParams();
    const [product, setProduct] = useState([]);
    const [categories, setCategories] = useState([]);
    // const [addressCat, setAddressCat] = useState('');
    // paginate
    const [offset, setOffset] = useState(0);
    const [data, setData] = useState([]);
    const [perPage] = useState(16);
    const [pageCount, setPageCount] = useState(0)
    //rating
    const [rating, setRating] = useState(1);

    // Price Min,Max
    const [minToPrice, setMinToPrice] = useState('');
    const [maxToPrice, setMaxToPrice] = useState('');


    // Nơi sản Xuất
    const [addProduct, setAddProduct] = useState('')


    const getData = async () => {

        let res;
        // if (rating == 1) {
        //     res = await ProductService.getProductRating(rating)
        // }
        if (param.name.includes('search=')) {
            res = await ProductService.getProduct(param.name.slice(7,), addProduct, '', minToPrice, maxToPrice)
        } else if (param.name !== 'product') {
            res = await ProductService.getProduct('', addProduct, param.name, minToPrice, maxToPrice)

        } else {
            res = await ProductService.getProduct('', '', '', '', '')
            if (maxToPrice !== '' || minToPrice !== '' || addProduct !== '' || rating !== '') {
                res = await ProductService.getProduct('', addProduct, '', minToPrice, maxToPrice)
            }
        }

        const data = res.data;

        const slice = data.slice(offset, offset + perPage)
        const postData = slice.map(item =>

            <div key={item.id} className="col-lg-3 col-md-6 col-sm-8">
                <Link to={"/detail/" + item.name}>
                    <div className="product" style={{ height: '350px' }}>
                        <a href="/" className="img-prod"><img className="img-fluid" src={item.image}
                            alt="Colorlib Template" style={{ height: '220px' }} />

                            <div className="overlay" />
                        </a>
                        <div className=" text py-3 pb-4 px-3 text-center">
                            <h3><a href="/">{item.name}</a></h3>
                            <div className="d-flex">
                                <div className="pricing">
                                    <p className="price"><span
                                        className="mr-2 price-dc">{(item.price + (item.price * 0.3)).toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span>
                                        <span className="price-sale">{item.price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></p>
                                </div>
                            </div>
                            <div className="bottom-area d-flex px-3">
                                <div className="m-auto d-flex">
                                    <Link to={"/detail/" + item.name}
                                        className="add-to-cart d-flex justify-content-center align-items-center text-center">
                                        <span><i className="fa-solid fa-bars"></i></span>
                                    </Link>
                                    <a href="/" className="buy-now d-flex justify-content-center align-items-center mx-1">
                                        <span><i className="fa-solid fa-cart-shopping"></i></span>
                                    </a>

                                </div>
                            </div>
                        </div>
                    </div>
                </Link>
            </div>
        )
        setData(postData)
        setPageCount(Math.ceil(data.length / perPage))
    }

    //Product
    useEffect(() => {
        ProductService.getProduct('', '', '', '', '')
            .then(response => response.data)
            .then((data) => {
                if (data.length > 0) {
                    setProduct(data)
                }
            });

    }, [])


    useEffect(() => {
        getData()
    }, [offset, minToPrice, maxToPrice, param, addProduct, rating])

    const handlePageClick = (e) => {
        const selectedPage = e.selected;
        const offset = selectedPage * perPage
        setOffset(selectedPage)
        setOffset(offset)
    };

    //catgories
    useEffect(() => {
        CategoriesService.getCategories()
            .then(response => response.data)
            .then((data) => {
                if (data.length > 0) {
                    setCategories(data)
                }

            });

    }, [])

    // origin filter the same element
    const _ = require("lodash");
    const result = _.uniqWith(product, function (arrVal, othVal) {
        return arrVal.origin === othVal.origin;
    });


    // rating
    const ratingChanged = (newRating) => {
        setRating(newRating)
    };
    console.log(rating)
    // price check
    const handlePrice = (event) => {
        setMinToPrice(event[0])
        setMaxToPrice(event[1])
    }
    // maxvaluePrice
    const maxIncome = (userWalletIncomes) => {
        let maxValue = 0;
        let maxKey = '';
        for (const [key, value] of Object.entries(userWalletIncomes)) {
            if (value > maxValue) {
                maxValue = value;
                maxKey = key
            }
        }
        return maxValue
    }

    const maxPrice = product.map((item, index) => {
        return item.price
    })


    const handleOrigin = (e, origin) => {

        e.preventDefault();
        if (origin !== "Việt Nam") {
            setAddProduct(origin)
        } else {
            setAddProduct("Việt Nam")
        }
    }
    console.log(product)
    return <div>
        {/* Product Section Begin */}
        <section className="products spad">
            <div className="container">
                <div className="row">
                    <div className="col-lg-3 col-md-5">
                        <div className="sidebar">
                            <div className="sidebar__item">

                            </div>
                            <div className="sidebar__item">
                                <h4>Giá</h4>
                                <RangeSlider max={maxIncome(maxPrice)} defaultValue={[29000, 75000]}
                                    onChange={handlePrice} />
                                <br />
                                <div>Khoảng Giá : {(minToPrice)?.toLocaleString('it-IT', {
                                    style: 'currency',
                                    currency: 'VND'
                                })} - {(maxToPrice)?.toLocaleString('it-IT', {
                                    style: 'currency',
                                    currency: 'VND'
                                })} </div>
                            </div>
                            <div className="sidebar__item sidebar__item__color--option">
                                <h4>Nơi sản xuất</h4>
                                {result.map((item, index) => {

                                    return (
                                        <div key={index} onClick={(e) => handleOrigin(e, item.origin)}
                                            className="sidebar__item__color sidebar__item__color--blue">
                                            <label htmlFor="white">
                                                {item.origin}
                                                <input type="radio" id="white" />
                                            </label>
                                        </div>
                                    )
                                })}
                            </div>

                            {/* <div className="sidebar__item">
                                <h4>Đánh giá</h4>
                                <ReactStars
                                    value={2} // số ngôi sao
                                    count={5}
                                    onChange={ratingChanged}
                                    size={26}
                                    isHalf={true}
                                    emptyIcon={<i className="bi bi-star"></i>}
                                    halfIcon={<i className="bi bi-star-half"></i>}
                                    fullIcon={<i className="bi bi-star-fill"></i>}
                                    activeColor="#ffd700"
                                />
                                
                            </div> */}

                        </div>
                    </div>
                    <div className="col-lg-9 col-md-8">

                        <div className="filter__item">
                            <div className="row">
                                <div className="col-lg-4 col-md-5">
                                    {/* <div className="filter__sort">
                                        <span>Sort By</span>
                                        <select>
                                            <option value={0}>Default</option>
                                            <option value={0}>Default</option>
                                        </select>
                                    </div> */}
                                </div>
                                <div className="col-lg-4 col-md-4">
                                    <div className="filter__found">
                                        <h6><span>{data.length}</span> Sản phẩm</h6>
                                    </div>
                                </div>
                                <div className="col-lg-4 col-md-3">
                                    <div className="filter__option">
                                        <span className="icon_grid-2x2" />
                                        <span className="icon_ul" />
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="row">


                            {data}
                            <ReactPaginate
                                previousLabel={"<<"}
                                nextLabel={">>"}
                                breakLabel={"..."}
                                breakClassName={"break-me"}
                                pageCount={pageCount}
                                marginPagesDisplayed={2}
                                pageRangeDisplayed={5}
                                onPageChange={handlePageClick}
                                containerClassName={"pagination"}
                                subContainerClassName={"pages pagination"}
                                activeClassName={"active"} />
                        </div>

                    </div>
                </div>
            </div>
        </section>
        {/* Product Section End */}
    </div>

}