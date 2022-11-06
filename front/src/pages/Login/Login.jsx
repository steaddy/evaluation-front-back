import {LockOutlined, UserOutlined} from '@ant-design/icons';
import React from "react";
import {Button, Checkbox, Form, Input} from 'antd';
import {useForm} from "react-hook-form";
import classes from './Login.module.css';
import {NavLink} from "react-router-dom";
import {useDispatch} from "react-redux";
import {setUser} from "../../store/slices/userSlice";

// auth test
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";


const Login = () => {
    const dispatch = useDispatch();

    // auth test (в тесте email вместо имени пользователя,
    // вводим его в поле username) !!!!
    const handleLogin = (email, password) => {
        const auth = getAuth();
        console.log(auth);
        signInWithEmailAndPassword(auth, email, password)
            .then(userCredential => {
                console.log(userCredential);
                const user = userCredential.user;
                }

            ).catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.log(error);
        });
    };




    const onFinish = (values) => {
        console.log('Received values of form: ', values.username, values.password);
        handleLogin(values.username, values.password);
    };
    return (
        <div className={classes["login"]}>
            <h1>Вход</h1>
            <Form
                name="normal_login"
                className={classes["login-form"]}
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
            >
                <Form.Item
                    name="username"
                    rules={[
                        {
                            required: true,
                            message: 'Пожалуйста, введите имя пользователя!',
                        },
                    ]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Имя пользователя"/>
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Пожалуйста, введите пароль!',
                        },
                    ]}
                >
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                        type="password"
                        placeholder="Пароль"
                    />
                </Form.Item>
                {/*
                <Form.Item>
                    <Form.Item name="remember" valuePropName="checked" noStyle>
                        <Checkbox>Remember me</Checkbox>
                    </Form.Item>

                    <a className="login-form-forgot" href="">
                        Forgot password
                    </a>
                </Form.Item>
*/}
                <Form.Item>
                    <Button type="primary" htmlType="submit" className={classes["btn login-form-button"]}>
                        Войти
                    </Button>
                    <NavLink to='/sign-up'>
                        <Button type='link' className={classes["btn"]}>Зарегистрироватьcя</Button>
                    </NavLink>
                </Form.Item>
            </Form>







        </div>
    );

};

export default Login;