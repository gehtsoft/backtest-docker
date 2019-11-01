import json, requests, sys, os.path
import rest_conf as conf

def get_datafiles():
    url = conf.BT_SERVER + "/data/list"        
    headers = {}
    return requests.get(url, headers=headers, verify=False, stream=True)

def find_datafile(name):
    resp = get_datafiles()
    # TBD: check resp.status_code here
    lst = resp.json()
    return name in lst


def delete_datafile(name):
    headers = {'Content-type': 'application/json'}    
    url = conf.BT_SERVER + "/data/delete"
    return requests.delete(url, headers=headers, data=json.dumps({"name" : name}), verify=False)

def add_datafile(name):
    url = conf.BT_SERVER + "/data/add"
    fname = conf.DATA_PATH + name

    headers = {}
    with open(fname, "rb") as f:
        params = dict (
            src = (name, f),
        )

        return requests.post(url, headers=headers, files=params, verify=False)
