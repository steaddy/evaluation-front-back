import { UploadOutlined } from '@ant-design/icons';
import { Button, Upload } from 'antd';
import React from "react";
import './LoadData.css';


const props = {
    action: 'https://178.170.242.214:8080/excel',
    listType: 'picture',
    previewFile(file) {
        console.log('Your upload file:', file);
        // Your process logic. Here we just mock to the same file
        return fetch('https://178.170.242.214:8080/excel', {
            method: 'POST',
            body: file,
        })
            .then((res) => res.json())
            .then(data => console.log(data));
    },
};



const LoadData = () => {
    return (
        <Upload {...props}>
            <Button icon={<UploadOutlined />}>Загрузите данные</Button>
        </Upload>
    );
};

export default LoadData;