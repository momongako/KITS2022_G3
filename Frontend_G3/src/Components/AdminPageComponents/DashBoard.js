import st from './dashboard.module.css'

export default function Dashboard(){
    return <div className="col">
        <div>
            <div className="container mt-5 mx-auto">
                <div className="row">
                    <div className="col-md-3 col-sm-6 col-xs-12 ">
                        <div className={`${st["panel"]} ${st["panel-dark"]} ${st["panel-colorful"]} ${"rounded-5"}`}>
                            <div className={`${st["panel-body"]} ${"text-center"}` } >
                                <p className={`${st["text-uppercase"]} ${st["mar-btm"]} ${st["text-sm"]} ${"text-white"}`} >Visit Today</p>
                                <i className="fa fa-users fa-3x" />
                                <hr />
                                <p className={`${st["h2"]} ${st["text-thin"]} ${"text-white"}`}>254,487</p>
                                <small><span className={`${st["text-semibold"]} ${"text-white"}`}>7%</span> Higher than yesterday</small>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-3 col-sm-6 col-xs-12 ">
                        <div className={`${st["panel"]} ${st["panel-danger"]} ${st["panel-colorful"]} ${"rounded-5"}`}>
                            <div className={`${st["panel-body"]} ${"text-center"}`}>
                                <p className={`${"text-uppercase"} ${st["mar-btm"]} ${st["text-sm"]} ${"text-white"}`}>Comments</p>
                                <i className="fa fa-comment fa-3x" />
                                <hr />
                                <p className={`${st["h2"]} ${st["text-thin"]} ${"text-white"}`}>873</p>
                                <small><span className={`${st["text-semibold"]} ${"text-white"}`}><i className="fa fa-unlock-alt fa-fw" /> 154</span> Unapproved comments</small>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-3 col-sm-6 col-xs-12 ">
                        <div className={`${st["panel"]} ${st["panel-primary"]} ${st["panel-colorful"]} ${"rounded-5"}`}>
                            <div className={`${st["panel-body"]} ${"text-center"}`}>
                                <p className={`${"text-uppercase"} ${st["mar-btm"]} ${st["text-sm"]} ${"text-white"}`}>New Order</p>
                                <i className="fa fa-shopping-cart fa-3x" />
                                <hr />
                                <p className={`${st["h2"]} ${st["text-thin"]} ${"text-white"}`}>2,423</p>
                                <small><span className={`${st["text-semibold"]} ${"text-white"}`}><i className="fa fa-shopping-cart fa-fw" /> 954</span> Sales in this month</small>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-3 col-sm-6 col-xs-12 ">
                        <div className={`${st["panel"]} ${st["panel-primary"]} ${st["panel-colorful"]} ${"rounded-5"}`}>
                            <div className={`${st["panel-body"]} ${"text-center"}`}>
                                <p className={`${"text-uppercase"} ${st["mar-btm"]} ${st["text-sm"]} ${"text-white"}`}>Earning</p>
                                <i className="fa fa-dollar fa-3x" />
                                <hr />
                                <p className={`${st["h2"]} ${st["text-thin"]} ${"text-white"}`}>7,428</p>
                                <small><span className={`${st["text-semibold"]} ${"text-white"}`}><i className="fa fa-dollar fa-fw" /> 22,675</span> Total Earning</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
}