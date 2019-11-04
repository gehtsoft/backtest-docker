import json, requests, sys, os.path, os
import rest_conf as conf
import bt_rest as bt
import strategy_rest as strategy
import unittest
import time


class BtTest(unittest.TestCase):

    def run_bt(self, name, proj):
        try:
            resp = bt.run_bt(name, proj)
            rc = bt.find_bt(name)
            self.assertTrue(rc, "New bt {0} not found".format(name))

            os.makedirs(conf.OUT_PATH, exist_ok = True)
            
            input_file = conf.OUT_PATH + name + ".input"
            resp = bt.get_input(name, input_file)
            self.check_file(input_file)
            
            out_file = conf.OUT_PATH + name + ".out"
            resp = bt.get_output(name, out_file)
            self.check_file(out_file)
            
            stat_file = conf.OUT_PATH + name + ".stat"
            resp = bt.get_stat(name, stat_file)
            self.check_file(stat_file)

            log_file = conf.OUT_PATH + name + ".log"
            resp = bt.get_log(name, log_file)
            self.check_file(log_file)

            resp = bt.del_bt(name)    
            rc = bt.find_bt(name)
            self.assertTrue(not rc, "Deleted bt {0} is still here".format(name))

        except Exception as e:
            self.fail(str(e))

    def test_simple_bt(self):
        name = conf.BT_NAME  + "-" + str(int(time.time()))
        proj = conf.BT_PROJ
        self.run_bt(name, proj)

    def test_pkg_bt(self):
        stg_name = conf.PKG_ADD
        resp =  strategy.add_strategy(stg_name)
        self.assertEqual(resp.status_code, 200, "Invalid response code {0}, {1}".format(resp.status_code, resp.content))

        name = conf.BT_PKG_NAME  + "-" + str(int(time.time()))
        proj = conf.BT_PKG_PROJ
        self.run_bt(name, proj)

        resp_del = strategy.delete_strategy(stg_name)
        self.assertEqual(resp_del.status_code, 200, "Invalid response delete code {0}, {1}".format(resp_del.status_code, resp_del.content))

    
    def check_file(self, path):
        ok = os.path.getsize(path) > 0
        self.assertTrue(ok, "Empty file {0}".format(path))
        os.remove(path)

    
if __name__ == "__main__":
    unittest.main()
