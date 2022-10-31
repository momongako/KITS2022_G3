import React from 'react';
import styles from './deleteModal.module.css'
import {useNavigate} from "react-router-dom";

// {`${styles[]}`}


export default function DeleteConfirmModal({onCancel,reset,selectedID,linkToAPI,onshow}){
    let navigate = useNavigate();
    const deleteItem = () => {
        let method = 'DELETE';
        const id = selectedID;


        const requestOptions = {
            method: method,
        };
        fetch(
            linkToAPI + id,
            requestOptions
        )
            .then((response) => {
                console.log(response)
                response.json()
            })
            .then((data) => {
                console.log(data);
                navigate(-1);
            });
        reset();
    };

    return <>
        <div style={{
            width: '99vw',
            position: 'absolute',
            backgroundColor: 'rgba(0,0,0,0.7)',
            left: 0,
            top: 0,
            textAlign: 'center',
            display:"flex",
            height:'100vh'
        }} onClick={onCancel}>
            <div className={`${styles["modal-container"]}`} onClick={(event)=>event.stopPropagation()}>
                <div className={`${styles["modal-dialog"]} ${styles["modal-confirm"]}`}>
                    <div className={`${styles["modal-content"]}`}>
                        <div className={`${styles["modal-header"]} ${"flex-column"}`} >
                            <div className={`${styles["icon-box"]}`}>
                                <i className="material-icons">&#xE5CD;</i>
                            </div>
                            <h4 className={`${styles["modal-title"]} ${"w-100"}`}>Are you sure?</h4>
                        </div>
                        <div className={`${styles["modal-body"]}`}>
                            <p className={styles["modal-body-main-text"]}>Do you really want to delete this records? This process cannot be undone.</p>
                        </div>
                        <div className={`${styles["modal-footer"]} ${"justify-content-center"}`} >
                            <button type="button" className="btn btn-secondary mx-3" onClick={onCancel}>Cancel</button>
                            <button type="button" className="btn btn-danger mx-3" onClick={deleteItem}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </>
}