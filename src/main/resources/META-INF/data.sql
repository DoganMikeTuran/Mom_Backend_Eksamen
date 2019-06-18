/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  dmt1
 * Created: 16-Jun-2019
 */

USE seed;
INSERT INTO users (user_name, user_pass) VALUES ('user', 'test');
INSERT INTO users (user_name, user_pass) VALUES ('admin', 'test');
INSERT INTO roles (role_name) VALUES ('user');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('user', 'user');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'admin');
