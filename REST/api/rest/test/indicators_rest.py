import json, requests, sys, os.path
import rest_conf as conf

def get_indicators():
    url = conf.BT_SERVER + "/indicator/list"        
    headers = {}
    return requests.get(url, headers=headers, verify=False, stream=True)

def find_indicator(name):
    resp = get_indicators()
    # TBD: check resp.status_code here
    lst = resp.json()
    return name in lst


def delete_indicator(name):
    headers = {'Content-type': 'application/json'}    
    url = conf.BT_SERVER + "/indicator/delete"
    return requests.delete(url, headers=headers, data=json.dumps({"name" : name}), verify=False)

def add_indicator(name):
    url = conf.BT_SERVER + "/indicator/add"
    fname = conf.DATA_PATH + name

    headers = {}
    with open(fname, "rb") as f:
        params = dict (
            src = (name, f),
        )

        return requests.post(url, headers=headers, files=params, verify=False)
