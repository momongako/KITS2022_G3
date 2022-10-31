export default function AdminLogin(){
    return <>
        <form className={"text-center my-5 h1"}>
            <div>
                <label className={"col-2 text-left my-2"}>Email:</label>
                <input type='email' id='email' name='email'/>
            </div>
            <div>
                <label className={"col-2 text-left my-2"}>Password:</label>
                <input type='password' name='password'/>
            </div>
            <input className={"my-5"} type="submit" value="Login"/>
        </form>
    </>
}
