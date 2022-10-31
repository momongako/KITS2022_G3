import React from 'react';

const Footer = () => {
    return (
        <>
            {/* Footer Section Begin */}
            <footer className="footer spad">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-3 col-md-6 col-sm-6">
                            <div className="footer__about">
                                <div className="footer__about__logo">
                                    <a href="./index.html"><img src="img/logo.png" alt="" /></a>
                                </div>
                                <ul>
                                    <li>Địa chỉ: Peakview Tower, Phố Hoàng Cầu, Ô Chợ Dừa, Đống Đa, Hà Nội, Việt Nam</li>
                                    <li>Điện thoại: +65 11.188.888</li>
                                    <li>Email: G3Mark@gmail.com</li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
                            <div className="footer__widget">
                                <h6>Useful Links</h6>
                                <ul>
                                    <li><a href="/">About Us</a></li>
                                    <li><a href="/">About Our Shop</a></li>
                                    <li><a href="/">Secure Shopping</a></li>
                                    <li><a href="/">Delivery infomation</a></li>
                                    <li><a href="/">Privacy Policy</a></li>
                                    <li><a href="/">Our Sitemap</a></li>
                                </ul>
                                <ul>
                                    <li><a href="/">Who We Are</a></li>
                                    <li><a href="/">Our Services</a></li>
                                    <li><a href="/">Projects</a></li>
                                    <li><a href="/">Contact</a></li>
                                    <li><a href="/">Innovation</a></li>
                                    <li><a href="/">Testimonials</a></li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-lg-4 col-md-12">
                            <div className="footer__widget">
                                <h6>Join Our Newsletter Now</h6>
                                <p>Get E-mail updates about our latest shop and special offers.</p>
                                <form action="/">
                                    <input type="text" placeholder="Enter your mail" />
                                    <button type="submit" className="site-btn">Subscribe</button>
                                </form>
                                <div className="footer__widget__social">
                                    <a href="/"><i className="fa-brands fa-facebook"></i></a>
                                    <a href="/"><i className="fa-brands fa-twitter"></i></a>
                                    <a href="/"><i className="fa-brands fa-linkedin-in"></i></a>
                                    <a href="/"><i className="fa-brands fa-pinterest-p"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
            {/* Footer Section End */}

        </>
    );
};

export default Footer;