/* eslint-disable react-hooks/exhaustive-deps */
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';

const AdminEditForm = ({itemSelected, selectedID, linkToAPI, entity, columns, fields, onCancel,reset}) => {
    const [item, setItem] = useState(itemSelected);

    let navigate = useNavigate();


    const saveItem = () => {
        let method = 'POST';
        let id = '';
        if (item) {
            method = 'PUT';
            id = selectedID;
        }
        const requestOptions = {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(item),
        };
        fetch(
            linkToAPI + id,
            requestOptions
        )
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                navigate(-1);
            });
        reset();
    };

    const handleChange = (event) => {
        console.log(event);
        const target = event.target;
        const value = target.value;
        const name = target.name;

        let data = {...item};
        data[name] = value;
        console.log(name, value);
        setItem(data);
    };

    return (
        <>
            <div style={{
                width: '99vw',
                position: 'absolute',
                backgroundColor: 'rgba(0,0,0,0.7)',
                left: 0,
                top: 0,
                textAlign: 'center',
                display:"flex"

            }} onClick={onCancel}>
                <span className={"col-3 p-0"} onClick={onCancel}/>
                <div className={`${"col-7 mx-0 py-5 border bg-white"}`} style={{zIndex: 200,position:'relative'}} onClick={(event)=>event.stopPropagation()}>
                    <div className="col-12 mx-0">
                        <h2>
                            <strong>{selectedID ? 'EDIT ' + entity.toUpperCase() : 'NEW ' + entity.toUpperCase()}</strong>
                        </h2>
                        <br/>
                        <div className="px-auto col-12" style={{position: 'relative'}}>
                            <table className="table table-user-information" style={{left: 0,right: 0,position:'relative',display:'inline-block'}}>
                                <tbody className={"col-12"}>
                                {fields.map(
                                    (field, index) => (
                                        <>
                                            <tr key={index} className={"col-12"}>
                                                {columns[index]!==columns[0]
                                                    ?<>
                                                    <td className={"mt-3 align-middle"} style={{maxWidth:'fit-content'}}>
                                                        <strong className={" mt-3"}>{columns[index]}</strong>
                                                    </td>
                                                    <td className={"mx-0 text-center col-10"} >
                                                        <input
                                                            type="text"
                                                            value={item!=null
                                                                ?item[field]!==null&&typeof item[field]==='object'
                                                                    ?item[field].id
                                                                    :JSON.stringify(item[field])
                                                                :""}
                                                            className="form-control col-xl-8 col-lg-4 col-md-2 col-sm-3 ms-3"
                                                            name={field}
                                                            placeholder={item!=null?item[field]!=='object'&&!Array.isArray(item[field])?item[field]:field+'ID':field}
                                                            onChange={(e) => handleChange(e)}
                                                        />
                                                    </td></>
                                                    :null}

                                            </tr>
                                        </>
                                    )
                                )}
                                </tbody>
                            </table>
                            <div className={"text-center"}>
                                <button
                                    type="button"
                                    className="btn btn-primary btn-lg"
                                    onClick={() => saveItem()}
                                >
                                    Save
                                </button>
                                <span className="col-6"> </span>
                                <button type="button" className="btn btn-secondary btn-lg" onClick={onCancel}>
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <span className={"col-2 p-0"} onClick={onCancel}></span>
            </div>
        </>
    );
};
export default AdminEditForm;
