import {Link} from "react-router-dom";
import st from '../Css/Error.module.css'

export default function Error({errorCode,message}){
    return <>
        <div>
            <div className={`${st["error-header"]}`}></div>
            <div className={`${st["container"]}`}>
                <section className={`${st["error-container"]}] ${"text-center"}`}>
                    <span className={st["error-container-h1"]}>{errorCode}</span>
                    <d className={st["error-container-dark"]}></d>
                    <div className={st["error-divider"]}>
                        <span className={st["error-container-h2"]}>OOPS!!!!!</span>
                        <p className={st["description"]}>{message}</p>
                    </div>
                    <Link to ="/" className={st["return-btn"]}><i className="fa fa-home"/>Home</Link>
                </section>
            </div>
        </div>
        </>
}