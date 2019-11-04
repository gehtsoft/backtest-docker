import json, requests, sys, os.path
import rest_conf as conf
import time

def get_bt_list():
    url = conf.BT_SERVER + "/bt/list"        
    headers = {}
    return requests.get(url, headers=headers, verify=False, stream=True)

def find_bt(name):
    resp = get_bt_list()
    # TBD: check resp.status_code here
    lst = resp.json()
    return name.upper() in lst


def run_bt(id, file):

    url = conf.BT_SERVER + "/bt/run"
    fname = conf.DATA_PATH + file

    headers = {}
    with open(fname, "rb") as f:
        params = dict (
            name = id,
            src = (fname, f),
        )


        t0 = time.time()
        resp = requests.post(url, headers=headers, files=params, verify=False)
        if resp.status_code != 200: 
            raise Exception("Invalid response code", resp.status_code, resp.content)
        t1 = time.time()
        print("Backtesting done in", (t1 - t0), "seconds")
        return resp.content

def del_bt(id):
    url = conf.BT_SERVER + "/bt/" + id + "/delete"
    resp = requests.delete(url, verify=False)
    if resp.status_code != 200 and resp.status_code != 204:
        raise Exception("Invalid response code", resp.status_code, resp.content)
    return resp


def get_file(url, out):
    resp = requests.get(url, verify=False, stream=True)

    if resp.status_code != 200: 
        raise Exception("Invalid response code", resp.status_code, resp.content)

    with open(out, 'wb') as fd:
        for chunk in resp.iter_content(1024):
            fd.write(chunk)
    return True


def get_log(id, out):
    url = conf.BT_SERVER + "/bt/" + id + "/log"
    return get_file(url, out)

def get_stat(id, out):
    url = conf.BT_SERVER + "/bt/" + id + "/stat"
    return get_file(url, out)

def get_input(id, out):
    url = conf.BT_SERVER + "/bt/" + id + "/input"
    return get_file(url, out)

def get_output(id, out):
    url = conf.BT_SERVER + "/bt/" + id + "/output"
    get_file(url, out)

if __name__ == "__main__":
    r = get_bt_list()
    js = r.json()
    for b in js:
        print (b)