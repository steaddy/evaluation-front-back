import React from 'react';
import classes from './Footer.module.css';
import analit from '../../img/analit.png';
import depart from '../../img/depart.png';

const Footer = () => {
    return (
        <div className={classes['footer']}>
            <div className={classes["footer_main-container"]}>
                <div className={classes["footer_logo-container"]}>
                    <img src={analit}/>
                </div>
                <div className={classes["footer_logo-container"]}>
                    <img src={depart}/>
                    <p>Департамент экономической
                        политики и развития</p>
                </div>
            </div>
        </div>
    );
};

export default Footer;