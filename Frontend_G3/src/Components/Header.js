import React from 'react';
import { Link, useParams } from "react-router-dom";
import { useState, useEffect } from 'react';
import CategoriesService from '../services/CategoriesService';
import { CartProvider, useCart } from 'react-use-cart';
const Header = ({ isUser, onLogout }) => {
    const [isLoggedIn, setLoggedIn] = useState(isUser)
    // library cart
    const { isEmpty, totalUniqueItems, items, updateItemQuantity, removeItem, cartTotal } = useCart();
    let param = window.location.pathname;
    let params = useParams('')
    const defaultDisplay = (param === "/" || params === "/" ? 'block' : 'none')
    const defaultHeader = (param !== "/login" || params !== "/login" ? 'block' : 'none')
    const [displays, setDisplay] = useState(defaultDisplay);
    const [onlyNav, setOnlyNav] = useState(defaultHeader);
    // Search
    const [search, setSearch] = useState('')
    useEffect(() => setDisplay(defaultDisplay), [param, params])

    const haldeAllDe = () => {
        // console.log('>>> check ', displays);
        setDisplay(displays === 'block' ? 'none' : 'block');
    }
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        CategoriesService.getCategories()
            .then(response => response.data)
            .then((data) => {
                if (data.length > 0) {
                    setCategories(data)
                }
            });

    }, [])
    // console.log('>>> check categories :', categories)
    // console.log(search)
    console.log(cartTotal)
    return (
        <>
            {/* Header Section Begin */}
            <header className="header">
                <div className="header__top">
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-6 col-md-6">
                                <div className="header__top__left">
                                    <ul>
                                        <li><i className="fa fa-envelope" /> G3Mark@gmail.com</li>
                                        <li>Ship tận nơi với mọi đơn hàng</li>
                                    </ul>
                                </div>
                            </div>
                            <div className="col-lg-6 col-md-6">
                                <div className="header__top__right">
                                    <div className="header__top__right__social">
                                        <Link to="/"><i className="fa-brands fa-facebook"></i></Link>
                                        <Link to="/"><i className="fa-brands fa-twitter"></i></Link>
                                        <Link to="/"><i className="fa-brands fa-linkedin-in"></i></Link>
                                        <Link to="/"><i className="fa-brands fa-pinterest-p"></i></Link>
                                    </div>
                                    <div className="header__top__right__language">
                                        <img src={require(`../img/rsz_1rsz_quoc-ky-viet-nam.jpg`)} alt="" />
                                        <div>Việt Nam</div>
                                        <span className="arrow_carrot-down" />
                                        <ul>
                                            <li><Link to="/">Việt Nam</Link></li>
                                            <li><Link to="/">English</Link></li>
                                        </ul>
                                    </div>
                                    <div className="header__top__right__auth">
                                        {!isLoggedIn && <div className="header__top__right__auth">
                                            <Link to="/login"><i className="fa fa-user" /> Login</Link>
                                        </div>}
                                        {isLoggedIn && <><div className="header__top__right__auth">
                                            <Link to="/profile"><i className="fa fa-user" /> Profile</Link>
                                        </div>
                                            <div className="header__top__right__auth ms-3" onClick={() => { onLogout(); setLoggedIn(false) }} style={{ cursor: "pointer" }}>
                                                Log Out
                                            </div></>}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="container">
                    <div className="row">
                        <div className="col-lg-3">
                            <div className="header__logo">
                                <Link to="/">
                                    <img src={require(`../img/rsz_logo-bg-white.png`)} alt="" />

                                </Link>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <nav className="header__menu">
                                <ul>
                                    <li className="active"><Link to="/">Trang chủ</Link></li>
                                    <li><Link to="/shops">Siêu thị</Link></li>

                                    <li><Link to="/shop/product">Sản phẩm</Link></li>
                                    <li><Link to="/contact">Thông tin</Link></li>
                                </ul>
                            </nav>
                        </div>
                        <div className="col-lg-3">
                            <div className="header__cart">
                                <ul>
                                    <li><Link to="/cart"><i className="fa fa-shopping-bag" /> <span>{(isEmpty) ? 0 : totalUniqueItems}</span></Link></li>
                                </ul>
                                <div className="header__cart__price">Giá: <span>{cartTotal?.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></div>
                            </div>
                        </div>
                    </div>
                    <div className="humberger__open">
                        <i className="fa fa-bars" />
                    </div>
                </div>
            </header>
            {/* Header Section End */}
            {/* Hero Section Begin */}

            <section className="hero" style={{ display: onlyNav }}>
                <div className="container">
                    <div className="row">


                        <div id="category-select" className="col-lg-3">
                            <div className="hero__categories" onClick={haldeAllDe}>
                                <div className="hero__categories__all">
                                    <i className="fa fa-bars" />
                                    <span>Các Loại Sản phẩm</span>
                                </div>

                                <ul className={param === "/" ? 'd-block' : `d-${displays}`}  >
                                    {categories != null ?
                                        categories.map((item, index) => {

                                            return (
                                                <>

                                                    <li key={index + 1}><Link to={"/shop/" + item.name}>{item.name}</Link></li>

                                                </>
                                            )

                                        })

                                        :
                                        ""
                                    }

                                </ul>

                            </div>
                        </div>


                        <div className="col-lg-9">
                            <div className="hero__search">
                                <div className="hero__search__form">
                                    {/* Search */}
                                    <form action="/">
                                        <div className="hero__search__categories">
                                            Tên sản Phẩm
                                            <span className="arrow_carrot-down" />
                                        </div>
                                        <input type="text" value={search} onChange={(event) => setSearch(event.target.value)} placeholder="Nhập tên sản phẩm" />
                                        <Link to={'/shop/search=' + search}><button className="site-btn">Tìm Kiếm</button></Link>
                                    </form>
                                    {/* end search */}
                                </div>
                                <div className="hero__search__phone">
                                    <div className="hero__search__phone__icon">
                                        <i className="fa fa-phone" />
                                    </div>
                                    <div className="hero__search__phone__text">
                                        <h5>0966875132</h5>
                                        <span>Hỗ Trợ 8h - 21h30</span>
                                    </div>
                                </div>
                            </div>
                            {/* banner */}
                            <div id="carouselExampleSlidesOnly" className={param === "/" ? 'carousel slide d-block' : 'carousel slide d-none'} data-ride="carousel">
                                <div className="carousel-inner">
                                    <div className="carousel-item active">
                                        <img className="d-block w-100" src={require(`../img/hero/banner.jpg`)} alt="First slide" />
                                        <div id="banner-hero" className="carousel-caption d-none d-md-block">
                                            <span>Trái cây tươi</span>
                                            <h2>Rau Quả <br />100% Hữu cơ</h2>
                                            <p>Nhận và giao hàng</p>
                                            <Link to="/" className="primary-btn">Mua Ngay</Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            {/* <div className="hero__item set-bg" data-setbg="img/hero/banner.jpg">
                                <div className="hero__text">
                                    <span>FRUIT FRESH</span>
                                    <h2>Vegetable <br />100% Organic</h2>
                                    <p>Free Pickup and Delivery Available</p>
                                    <Link to="/" className="primary-btn">SHOP NOW</Link>
                                </div>
                            </div> */}
                        </div>
                    </div>
                </div>
            </section>
            {/* Hero Section End */}

        </>
    );
};

export default Header;