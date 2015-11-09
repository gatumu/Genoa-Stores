<?php
@mysql_connect("localhost","root","");
@mysql_select_db("genoa_stores");

if(!isset($_POST['json']))
	{
		exit;
	}

extract($_POST);
$data = json_decode($json);
$response = array();
for($i=0;$i<count($data); $i++)
	{
		$uid = $data[$i]->uid;
		$date = $data[$i]->date;
		$product = $data[$i]->product;
		$qty = $data[$i]->qty;
		$customer_names = $data[$i]->customer_names;
		$customer_location = $data[$i]->customer_location;
		$sales_agent = "agent5000";
		$sql = "INSERT INTO sales VALUES ('', '$uid', '$date', '$product', '$qty', '$customer_names', '$customer_location', '$sales_agent')";
		$res = mysql_query($sql);
			if($res)
				{
					$response[] = array("uid"=>$uid, "status"=>"yes");
				}
			else
				{
					$response[] = array("uid"=>$uid, "status"=>"no");
				}
	}
echo json_encode($response);
?>