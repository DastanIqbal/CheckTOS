<?php

/**
 * Created by IntelliJ IDEA.
 * User: IQBAL-MEBELKART
 * Date: 8/23/2016
 * Time: 6:19 PM
 */

require '../vendor/config.php';

class DB
{

    private $dbConnection;

    public function __construct()
    {

        $this->dbConnection = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_DATABASE);
    }

    public function insertTOS($result){
        $sql = "insert into tos_user_checkup_info(checkup_info,timestamp_value,user_id) values('"
            . $result['user_checkup_info'] . "','" . $result['timestamp'] . "','" . $result['user_id'] . "')";
        $rs = $this->dbConnection->query($sql);
        return $rs;
    }
}