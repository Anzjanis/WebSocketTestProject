package org.example.models.subscriber;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.cucumber.datatable.DataTable;
import org.example.helpers.TestDataGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscribe {
    private String event;
    private Integer reqid;
    private List<String> pair;
    private Subscription subscription;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getReqid() {
        return reqid;
    }

    public void setReqid(Integer reqid) {
        this.reqid = reqid;
    }

    public List<String> getPair() {
        return pair;
    }

    public void setPair(List<String> pair) {
        this.pair = pair;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscribe buildSubscribeModel(DataTable dataTable) {
        var map = dataTable.entries().get(0);

        Subscribe subscribe = new Subscribe();
        var x = map.get("interval");
        Subscription subscription = new Subscription();
        subscribe.subscription = subscription;
        subscribe.reqid = TestDataGen.generateInteger(map.get("reqid"), 5);
        subscribe.pair = Arrays.asList(map.get("pair").split("\\s*,\\s*"));

        subscription.setDepth(((map.get("depth")).equals("null")) ? null : (Integer.valueOf(map.get("depth"))));

        subscription.setInterval(((map.get("interval")).equals("null")) ? null : (Integer.valueOf(map.get("interval"))));
        subscription.setName(map.get("name"));
        subscription.setRatecounter(((map.get("ratecounter")).equals("null")) ? null : (Boolean.valueOf(map.get("ratecounter"))));
        subscription.setSnapshot(((map.get("snapshot")).equals("null"))? null : (Boolean.valueOf(map.get("snapshot"))));

        return subscribe;
    }

}
