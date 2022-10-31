import React, {useEffect, useState} from "react";
import ComboBox from "./ComboBox";
import AdminService from "../../services/AdminService";
import RangeSlider from "rsuite/RangeSlider";

export default function AdminNewOrder() {
    const [items, setItems] = useState(null)
    const [listProduct, setListProduct] = useState(null)
    const [listUser, setListUser] = useState(null)
    const [selectedItem, setSelectedItem] = useState()
    const [selectedUser, setSelectedUser] = useState()
    const [quantity, setQuantity] = useState(1)
    const [CurrentProducts, setCurrentProducts] = useState(new Map());
    const [totalPrice, setTotalPrice] = useState(null);
    const [totalQuantity, setTotalQuantity] = useState(null);
    const [isMember, setIsMember] = useState(false);
    const [isNewMember, setIsNewMember] = useState(false);
    const [usePoint, setUsePoint] = useState(false);
    const [pointUse, setPointUse] = useState(0);

    async function setData() {
        const productData = (await AdminService.fetchOnlyData("product")).data;
        const userData = (await AdminService.fetchOnlyData("user")).data
        return {product: productData, user: userData}
    }

    useEffect(() => {
        setData().then((data) => {
            setListProduct(data.product)
            setListUser(data.user)
        })
    }, [])

    async function onSelectProduct(e, item) {
        e.preventDefault();
        await setSelectedItem(item)
    }

    async function onSelectUser(e, item) {
        e.preventDefault();
        await setSelectedUser(item)
    }

    async function onAdjust(item, sign, quantity) {
        (!CurrentProducts.has(item?.id)
            ? CurrentProducts.set(item?.id, {
                id: item?.id,
                name: item?.name,
                quantity: quantity,
                price: item?.price
            })
            : CurrentProducts.set(item?.id, {
                id: item?.id,
                name: item?.name,
                quantity: sign === '+' ?
                    ((CurrentProducts.get(item?.id).quantity) + quantity)
                    : sign === '-' ? ((CurrentProducts.get(item?.id).quantity) - quantity) >= 0
                        ? ((CurrentProducts.get(item?.id).quantity) - quantity)
                        : 0 : 0
                ,
                price: item?.price
            }))
        const cart = []
        let tPrice = 0;
        let tQuantity = 0;
        for (const item of CurrentProducts.values()) {
            if (item.quantity !== 0) cart.push(item)
            tPrice += item.price * item.quantity
            tQuantity += item.quantity
        }
        setItems(cart)
        setTotalPrice(tPrice)
        setTotalQuantity(tQuantity)
    }

    async function AddProductHandler(e) {
        await e.preventDefault();
        await setQuantity(eval(e.target.parentElement.children[1].value))
        await onAdjust(selectedItem, '+', quantity)
    }

    return listProduct !== null
        ? <div className={'mainContent_ mainContent_dashboard-content container'}>
            <div className={'container my-5 mx-auto'}>
                <div className={'container row'}>
                    <ComboBox list={listProduct} placeholder={"Products"} onSelect={onSelectProduct}/>
                    <input type='text' className={'col rounded-3 text-center mx-1'}
                           onChange={e => setQuantity(eval(e.target.value) > 0 ? eval(e.target.value) : 1)}
                           value={quantity}/>
                    <button className={"btn btn-primary float-end col-2"} onClick={e => AddProductHandler(e)}>Add
                        Product
                    </button>
                </div>
                <section className="shoping-cart spad">
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="shoping__cart__table">
                                    <table>
                                        <thead>
                                        <tr>
                                            <th className="shoping__product">Products</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th className={'text-center'}>Total</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        {items && items?.map((item, index) => {
                                            return (
                                                <tbody key={index}>
                                                <tr>
                                                    <td className="shoping__cart__item">
                                                        <img src="" alt=""/>
                                                        <h5>{item.name}</h5>
                                                    </td>
                                                    <td className="shoping__cart__price">
                                                        {item?.price.toLocaleString('it-IT', {
                                                            style: 'currency',
                                                            currency: 'VND'
                                                        })}
                                                    </td>
                                                    <td className="shoping__cart__quantity">
                                                        <div className="quantity">
                                                            <button
                                                                onClick={() => {
                                                                    onAdjust(item, '-', 1).then(() => console.log('decrement'))
                                                                }}
                                                                className="btn btn-info ms-2">
                                                                -
                                                            </button>
                                                            <span className="mx-1 text-center">
                                                                {item.quantity}
                                                            </span>
                                                            <button
                                                                onClick={() => {
                                                                    onAdjust(item, '+', 1).then(() => console.log('increment'))
                                                                }}
                                                                className="btn btn-info ms-2">
                                                                +
                                                            </button>
                                                        </div>
                                                    </td>
                                                    <td className="shoping__cart__total">
                                                <span
                                                    className={"ms-5"}>{(item.price * item.quantity).toLocaleString('it-IT', {
                                                    style: 'currency',
                                                    currency: 'VND'
                                                })}</span>
                                                    </td>
                                                    <td>
                                                        <button
                                                            onClick={() => {
                                                                onAdjust(item, 0, 0).then(() => console.log('remove'))
                                                            }}
                                                            className="btn btn-danger ms-2">
                                                            Remove Item
                                                        </button>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            )
                                        })}
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-lg-5 me-5 border rounded border-primary">
                                <h4 className={'text-center border-2 border-bottom pb-3 border-black'}>
                                    Thông tin khách hàng
                                </h4>
                                <div className={'container row'}>
                                    {isMember &&
                                        <ComboBox list={listUser} placeholder={"Members"} onSelect={onSelectUser}/>}
                                    <div className={'col align-middle'}>
                                        <input type="checkbox" id="member" name="member" value="member"
                                               onChange={() => setIsMember(!isMember)}/>
                                        <label className={'h5 mx-1'} htmlFor="member">Thành viên ?</label>
                                    </div>
                                    {!isMember && <div className={'col align-middle'}>
                                        <input type="checkbox" id="newMember" name="newMember" value="newMember"
                                               onChange={() => setIsNewMember(!isNewMember)}/>
                                        <label className={'h5 mx-1'} htmlFor="newMember">Đăng ký thành viên ?</label>
                                    </div>}
                                </div>
                                {!isMember && !isNewMember && <div className={'container text-center'}>
                                    <span className='align-middle text-center display-1 fw-bolder'>Khách lẻ</span>
                                </div>}
                                {isMember && <table className='mt-5'>
                                    <thead>
                                    <tr>
                                        <th>Họ tên</th>
                                        <td>{selectedUser?.name}</td>
                                    </tr>
                                    <tr>
                                        <th>Hạng</th>
                                        <td>{selectedUser?.ranking?.name}</td>
                                    </tr>
                                    <tr>
                                        <th>
                                            Điểm hiện có
                                            <div>
                                                {usePoint && <>
                                                    <span>0</span>
                                                    <input type="range" min="0" max={`${selectedUser?.point}`}
                                                           className="slider" id="pointRange" value={pointUse}
                                                           onChange={e => setPointUse(e.target.value)}/>
                                                    <span>{pointUse}</span></>}
                                            </div>
                                        </th>
                                        <td>{selectedUser?.point || 0}
                                            {selectedUser?.point > 100000 ? <><input className='ms-3' type="checkbox"
                                                                                   id="member" name="member"
                                                                                   value="member"
                                                                                   onChange={() => setUsePoint(!usePoint)}/>
                                                <label className={'mx-1'} htmlFor="member">Dùng điểm ?</label></> : ''}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Điểm tích luỹ từ đơn hàng</th>
                                        <td>{totalPrice / 100}</td>
                                    </tr>

                                    </thead>
                                </table>}
                            </div>
                            <div className="col-lg-6 ms-2  border rounded border-primary">
                                <h4 className={'text-center border-2 border-bottom pb-3 border-black'}>Thông tin đơn
                                    hàng</h4>
                                <div className="shoping__checkout ">
                                    <h5>Tổng đơn hàng <span
                                        className={'float-end text-primary'}>{totalQuantity ? totalQuantity + ' sản phẩm' : null}</span>
                                    </h5>
                                    <ul>
                                        <li>Tạm tính <span>{totalPrice?.toLocaleString('it-IT', {
                                            style: 'currency',
                                            currency: 'VND'
                                        }) || '0 VND'}</span></li>
                                        <li>Thuế VAT 10%<span>{(totalPrice / 10).toLocaleString('it-IT', {
                                            style: 'currency',
                                            currency: 'VND'
                                        })}</span></li>
                                        {isMember && selectedUser && <li>
                                            Khấu trừ thành viên
                                            - {selectedUser?.ranking?.name} (- {selectedUser?.ranking?.discount}%)
                                            <span>{(selectedUser?.ranking?.discount / 100 * totalPrice).toLocaleString('it-IT', {
                                                style: 'currency',
                                                currency: 'VND'
                                            })}</span></li>}
                                        {usePoint && pointUse > 0 && <li>
                                            Trừ điểm tích luỹ
                                            <span>{pointUse.toLocaleString('it-IT', {
                                                style: 'currency',
                                                currency: 'VND'
                                            })}</span></li>}
                                        <li>Tổng tiền
                                            <span>{((isMember && selectedUser
                                                ? usePoint && pointUse > 0
                                                    ? (totalPrice + totalPrice / 10 - selectedUser?.ranking?.discount / 100 * totalPrice - pointUse / 1)
                                                    : (totalPrice + totalPrice / 10 - selectedUser?.ranking?.discount / 100 * totalPrice)
                                                : totalPrice + totalPrice / 10)
                                            > 0
                                                ? (isMember && selectedUser
                                                    ? usePoint && pointUse > 0
                                                        ? (totalPrice + totalPrice / 10 - selectedUser?.ranking?.discount / 100 * totalPrice - pointUse / 1)
                                                        : (totalPrice + totalPrice / 10 - selectedUser?.ranking?.discount / 100 * totalPrice)
                                                    : totalPrice + totalPrice / 10)
                                                : 0).toLocaleString('it-IT', {
                                                style: 'currency',
                                                currency: 'VND'
                                            })}</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div> : <div className='h1 text-center align-baseline'>'loading...'</div>
}