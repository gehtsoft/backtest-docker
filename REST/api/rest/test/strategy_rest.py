import json, requests, sys, os.path
import rest_conf as conf

def get_strategies():
    url = conf.BT_SERVER + "/strategy/list"        
    headers = {}
    return requests.get(url, headers=headers, verify=False, stream=True)

def find_strategy(name):
    resp = get_strategies()
    # TBD: check resp.status_code here
    lst = resp.json()
    return name in lst


def delete_strategy(name):
    headers = {'Content-type': 'application/json'}    
    url = conf.BT_SERVER + "/strategy/delete"
    return requests.delete(url, headers=headers, data=json.dumps({"name" : name}), verify=False)

def add_strategy(name):
    url = conf.BT_SERVER + "/strategy/add"
    fname = conf.DATA_PATH + name

    headers = {}
    with open(fname, "rb") as f:
        params = dict (
            src = (name, f),
        )

        return requests.post(url, headers=headers, files=params, verify=False)


if __name__ == "__main__":
    r = get_strategies()
    js = r.json()
    for b in js:
        print (b)
