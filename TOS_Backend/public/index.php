<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';


$app = new \Slim\App();

$app->post('/api/getTOS', function (Request $request, Response $response) {
    $json = $request->getParsedBody();
    if ($json != null && $json['json'] != null) {
        $req = json_decode($json['json'], true);
        $db = new DB();
        $tos = calculateTOS($req);
        $req['probability'] = $tos;
        $result = array("user_checkup_info" => json_encode($req), "timestamp" => $req['timestamp'], "user_id" => 1);
        $rs = $db->insertTOS($result);
        if ($rs)
            $result = array("status" => 1, "tos_in_percent" => $tos);
        else
            $result = array("status" => 0, "msg" => "Server Error");
        $response->getBody()->write(json_encode($result));
    } else {

    }
    return $response;
});

$app->run();

function calculateTOS($req)
{
    $gender = $req['gender'];
    $drugs = $req['taking_drugs'];
    $migraines = $req['have_migraines'];
    $age = $req['is_below_age_16'];

    $tosValues = array($gender, $drugs, $migraines, $age);
    $calcPrctage = 0;
    $part = (100 / count($tosValues));
    foreach ($tosValues as $value) {
        if ($value) {
            $calcPrctage += $part;
        }
    }

    return round($calcPrctage, 2);
}
