import { useCart } from "react-use-cart";
import React from "react";
export default function Checkout() {
    const { isEmpty, totalUniqueItems, items, cartTotal, updateItemQuantity, removeItem } = useCart();

    return <>
        <section className="checkout spad">
            <div className="container">
                <div className="checkout__form">
                    <h4>Chi tiết thanh toán</h4>
                    <form action="#">
                        <div className="row">
                            <div className="col-lg-12 col-md-6">


                                <div className="checkout__input">
                                    <p>Nhập địa chỉ mới(nếu thay đổi)<span>*</span></p>
                                    <input type="text" placeholder=" Địa chỉ mới" className="checkout__input__add" />

                                </div>


                            </div>
                            <div className="col-lg-12 col-md-6">
                                <div className="checkout__order">
                                    <h4>Đơn hàng của bạn</h4>
                                    <div className="checkout__order__products">Sản Phẩm <span>Tổng tiền</span></div>
                                    {items.map((item) => {
                                        return (

                                            <ul>
                                                <li>{item.quantity} x {item.name} <span>{(item.price * item.quantity).toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></li>
                                            </ul>
                                        )
                                    })}

                                    <div className="checkout__order__subtotal">Tạm tính <span>{cartTotal.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></div>
                                    <div className="checkout__order__subtotal">Phí vận chuyển <span>{cartTotal > 0 ? (20000) : (0).toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></div>
                                    <div className="checkout__order__subtotal">Thuế 10%<span>{(cartTotal / 10)?.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></div>
                                    <div className="checkout__order__subtotal">Tổng tiền <span>{cartTotal > 0 ? (cartTotal + cartTotal / 10 + 20000) : (0).toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></div>
                                    <button type="submit" className="site-btn">Đăt hàng tận nơi</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </>
}