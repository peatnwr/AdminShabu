let express = require('express');
let app = express();
let bodyParser = require('body-parser');
let mysql = require('mysql');

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: true
}));

let dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'shabu'
});

dbConn.connect();

//main function
app.post('/createTable', function(req, res) {
    let table = req.body.table;
    let amountCus = req.body.amountCus;
    let codeTable = req.body.codeTable;

    dbConn.query('SELECT * FROM `customer` WHERE cus_table = ? AND cus_status != 1', table, function(error, results, fields){
        if(error) throw error;

        if(results && results.length){
            return res.status(400).send({ error: true, message: "This table have in database" })
        }else{
            dbConn.query('INSERT INTO `customer`(`cus_code`, `cus_amount`, `cus_table`) VALUES (?, ?, ?)', [codeTable, amountCus, table], function(error, results, fields){
                if(error) throw error;

                return res.send(results);
            });
        }
    });
});

app.get('/paymentStatus', function(req, res) {
    dbConn.query('SELECT `customer`.`cus_table`, `customer`.`cus_amount`, `order`.`order_id`, `order`.`order_price`, `order`.`cus_code` FROM `customer`, `order` WHERE `customer`.`cus_code` = `order`.`cus_code` AND `order`.`order_payment` = 0', function(error, results, fields){
        if(error) throw error;

        return res.send(results);
    });
});

app.put('/updatePaymentStatus/:order_id/:cus_code', function(req, res) {
    let orderId = req.params.order_id;
    let cusCode = req.params.cus_code;
    let date = req.body.date;

    if(!orderId){
        return res.status(400).send({ error: true, message: "Please provide order id" });
    }else{
        dbConn.query('UPDATE `order` SET `order_date` = ?, `order_payment` = 1 WHERE `order_id` = ?', [date, orderId], function(error, results, fields){
            if(error) throw error;
            dbConn.query('UPDATE `customer` SET `cus_status` = 1 WHERE `customer`.`cus_code` = ?', cusCode, function(error, results, fields){
                if(error) throw error;
                
                return res.send(results);
            });
        });
    }
});

app.get('/cookStatus', function(req, res) {
    dbConn.query('SELECT `customer`.`cus_table`, `food`.`food_name`, `orderdetail`.`orderdetail_qty`, `orderdetail`.`orderdetail_id` FROM `customer`, `food`, `orderdetail`, `order` WHERE `order`.`order_id` = `orderdetail`.`order_id` AND `customer`.`cus_code` = `order`.`cus_code` AND `orderdetail`.`food_id` = `food`.`food_id` AND `orderdetail`.`orderdetail_status` = 1', function(error, results, fields){
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/updateCookStatus/:orderdetail_id', function(req, res) {
    let orderDetailId = req.params.orderdetail_id;

    if(!orderDetailId){
        return res.status(400).send({ error: true, message: "Please provide orderdetail id" })
    }else{
        dbConn.query('UPDATE `orderdetail` SET `orderdetail_status` = 2 WHERE `orderdetail_id` = ?', orderDetailId, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        })
    }
});

app.get('/categoryFood', function(req, res) {
    dbConn.query('SELECT * FROM `foodcategory`', function(error, results, fields){
        if(error) throw error;

        return res.send(results);
    });
});

app.get('/food/:foodcategory_id', function(req, res) {
    let foodCategoryId = req.params.foodcategory_id

    if(!foodCategoryId){
        return res.status(400).send({ error: true, message: "Please provide food category id" });
    }else{
        dbConn.query('SELECT * FROM `food` WHERE foodcategory_id = ?', foodCategoryId, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

app.post('/insertFood/:foodcategory_id', function(req, res) {
    let foodCategoryId = req.params.foodcategory_id
    let foodName = req.body.food_name
    let foodImg = req.body.food_img
    let foodAmount = req.body.food_amount

    if(!foodCategoryId){
        return res.status(400).send({ error: true, message: "Please provide food category id" });
    }else{
        dbConn.query('INSERT INTO `food`(`food_name`, `food_img`, `food_amount`, `foodcategory_id`) VALUES (?, ?, ?, ?)', [foodName, foodImg, foodAmount, foodCategoryId], function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

app.put('/editFood/:food_id', function(req, res) {
    let foodId = req.params.food_id
    let foodName = req.body.food_name
    let foodAmount = req.body.food_amount
    let foodImage = req.body.food_img

    if(!foodId){
        return res.status(400).send({ error: true, message: "Please provide food id" });
    }else{
        dbConn.query('UPDATE `food` SET `food_name` = ?, `food_img` = ?, `food_amount` = ? WHERE `food_id` = ?', [foodName, foodImage, foodAmount, foodId], function(error, results, fields){
            if(error) throw error;
            
            return res.send(results);
        });
    }
});

app.delete('/deleteFood/:food_id', function(req, res) {
    let foodId = req.params.food_id

    if(!foodId){
        return res.status(400).send({ error: true, message: "Please provide food id" });
    }else{
        dbConn.query('DELETE FROM `food` WHERE `food_id` = ?', foodId, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        })
    }
});

app.get('/searchFood/:food_name/:foodcategory_id', function(req, res) {
    let foodName = req.params.food_name
    let foodCategoryId = req.params.foodcategory_id

    if(!foodName && !foodCategoryId){
        return res.status(400).send({ error: true, message: "Please provide food name" });
    }else{
        dbConn.query('SELECT * FROM `food` WHERE `food_name` LIKE "%"?"%" AND `foodcategory_id` = ?', [foodName, foodCategoryId], function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

app.get('/searchCategory/:foodcategory_name', function(req, res) {
    let foodCategoryName = req.params.foodcategory_name

    if(!foodCategoryName){
        return res.status(400).send({ error: true, message: "Please provide food category name" });
    }else{
        dbConn.query('SELECT * FROM `foodcategory` WHERE `foodcategory_name` LIKE "%"?"%"', foodCategoryName, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

//authentication function
app.post('/loginEmp', function(req, res) {
    let username = req.body.username;
    let password = req.body.password;

    dbConn.query('SELECT * FROM `employee` WHERE emp_username = ?', username, function(error, results, fields){
        if(error) throw error;

        if(results && results.length){
            let passwordFromDb = results[0].emp_password
            if(password == passwordFromDb){
                return res.send(results[0]);
            }else{
                return res.status(400).send({ error: true, message: "Password does not match" });
            }
        }else{
            return res.status(400).send({ error: true, message: "User does not exist" })
        }
    });
    
});

app.put('/editProfile/:emp_id', function(req, res) {
    let empId = req.params.emp_id;
    let name = req.body.emp_name;
    let username = req.body.emp_username;
    let tel = req.body.emp_tel;
    let img = req.body.emp_img;

    if(!empId){
        return res.status(400).send({ error: true, message: "Please provide emp id" });
    }else{
        dbConn.query('UPDATE `employee` SET `emp_name` = ?, `emp_tel` = ?, `emp_username` = ?, `emp_img` = ? WHERE `emp_id` = ?', [name, tel, username, img, empId], function(error, results, fields) {
            if(error) throw error;

            return res.send(results)
        });
    }
});

app.listen(3000, function(){
    console.log('Node api is running on port 3000');
});

module.exports = app;