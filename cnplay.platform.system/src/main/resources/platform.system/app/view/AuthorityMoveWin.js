/*
 * File: app/view/AuthorityMoveWin.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('platform.system.view.AuthorityMoveWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.AuthorityMoveWin',

    requires: [
        'platform.system.view.UserSelect',
        'Ext.form.Panel',
        'Ext.form.field.Hidden',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.form.FieldContainer',
        'Ext.form.field.Date',
        'Ext.form.field.Time',
        'Ext.toolbar.Toolbar'
    ],

    height: 420,
    width: 544,
    layout: 'border',
    title: '功能编辑',
    modal: true,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    region: 'center',
                    border: false,
                    layout: 'auto',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'hiddenfield',
                            fieldLabel: 'Label',
                            name: 'id'
                        },
                        {
                            xtype: 'panel',
                            border: false,
                            bodyBorder: false,
                            title: '',
                            titleCollapse: false,
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    width: 290,
                                    fieldLabel: '被授权人名称',
                                    labelAlign: 'right',
                                    name: 'text',
                                    emptyText: '请输入被授权人名称',
                                    validateBlank: true,
                                    listeners: {
                                        afterrender: {
                                            fn: me.onUserNameAfterRender,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    iconCls: 'icon-search',
                                    text: '查询',
                                    listeners: {
                                        click: {
                                            fn: me.onSearchClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'UserSelect',
                            margin: '2 0 3 105',
                            listeners: {
                                beforerender: {
                                    fn: me.onUserGridBeforeRender,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'fieldcontainer',
                            height: 53,
                            fieldLabel: '',
                            layout: {
                                type: 'table',
                                columns: 2
                            },
                            items: [
                                {
                                    xtype: 'datefield',
                                    fieldLabel: '开始日期',
                                    labelAlign: 'right',
                                    name: 'startDate',
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'timefield',
                                    fieldLabel: '',
                                    labelAlign: 'right',
                                    name: 'startTime',
                                    emptyText: '00:00:00',
                                    editable: false,
                                    format: 'H:i:s'
                                },
                                {
                                    xtype: 'datefield',
                                    fieldLabel: '结束日期',
                                    labelAlign: 'right',
                                    name: 'endDate',
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'timefield',
                                    fieldLabel: '',
                                    labelAlign: 'right',
                                    labelWidth: 0,
                                    name: 'endTime',
                                    emptyText: '00:00:00',
                                    editable: false,
                                    format: 'H:i:s'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onFormAfterRender,
                            scope: me
                        }
                    }
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    defaultAlign: 'right',
                    layout: {
                        type: 'hbox',
                        align: 'middle',
                        pack: 'end'
                    },
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 'icon-ok',
                            text: '确 定',
                            listeners: {
                                click: {
                                    fn: me.onBtnOkClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-cancel',
                            text: '取 消',
                            listeners: {
                                click: {
                                    fn: me.onBtnCancelClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onUserNameAfterRender: function(component, eOpts) {
        this.username = component;
    },

    onSearchClick: function(button, e, eOpts) {
        this.loadGrid();
    },

    onUserGridBeforeRender: function(component, eOpts) {
        this.userselect = component;
    },

    onFormAfterRender: function(component, eOpts) {
            this.form = component;
    },

    onBtnOkClick: function(button, e, eOpts) {
        var me = this;
        if(!me.validateForm()){
            return;
        }
        var selected = me.userselect.getSelectionModel().selected;
        var selecteditems = selected.items;
        if (selecteditems.length !== 1)
        {
            Common.alert(
                {
                    msg : "请选择用户",
                    icon : Ext.Msg.WARNING
                });
            return;
        }
        var id = this.form.getForm().findField('id').getValue();
        var startDate = this.form.getForm().findField('startDate').getValue();
        var startTime = this.form.getForm().findField('startTime').getValue();

        var endDate = this.form.getForm().findField('endDate').getValue();
        var endTime = this.form.getForm().findField('endTime').getValue();

        var startDateStr = Ext.util.Format.date(startDate,'Y-m-d')+' ' + Ext.util.Format.date(startTime,'H:i:s');
        var endDateStr = Ext.util.Format.date(endDate,'Y-m-d')+' ' + Ext.util.Format.date(endTime,'H:i:s');
        try{
            Common.ajax({
                component : me,
                message : '加载信息...',
                url : ctxp+'/manage/authority/save',
                params :
                {
                    id:id,
                    'toUserId' : selecteditems[0].data.id,
                    'startTimeStr':startDateStr,
                    'endTimeStr':endDateStr
                },
                callback : function(result)
                {
                    me.close();
                    me.loadGrid();
                }
            });
        }
        catch(error)
        {
            Common.show({title:'信息提示',html:error.toString()});
        }
    },

    onBtnCancelClick: function(button, e, eOpts) {
                this.close();
    },

    loadForm: function(id) {
        var me = this;
        if(id){
            this.toUserId = id;
            try{
                Common.ajax({
                component : me.form,
                message : '加载信息...',
                url : ctxp+'/manage/authority/loadById?id='+id,
                callback : function(result)
                {
                    me.form.getForm().reset();
                    me.form.getForm().setValues(result.rows);
                    var startTime = result.rows.startTimeStr;
                    var endTime = result.rows.endTimeStr;
                    var timeArr1 = startTime.split(" ");
                    var timeArr2 = endTime.split(" ");
                    me.form.getForm().findField("startDate").setValue(timeArr1[0]);
                    me.form.getForm().findField("startTime").setValue(timeArr1[1]);
                    me.form.getForm().findField("endDate").setValue(timeArr2[0]);
                    me.form.getForm().findField("endTime").setValue(timeArr2[1]);
                    me.userselect.toUserId = result.rows.toUserId;
                    me.loadGrid();
                }
            });
            }catch(e){

            }
        }
    },

    loadGrid: function() {

        var me = this;
        try{
          me.userselect.loadData(me.username.getValue());
        }catch(error){
             Common.show({title:'信息提示',html:error.toString()});
        }

    },

    validateForm: function() {
        var startDate = this.form.getForm().findField('startDate').getValue();
        var startTime = this.form.getForm().findField('startTime').getValue();

        if(!startDate || !startTime){
            Common.alert({msg:'请输入开始时间！',icon:Ext.MessageBox.WARNING});
            return false;
        }

        var endDate = this.form.getForm().findField('endDate').getValue();
        var endTime = this.form.getForm().findField('endTime').getValue();

        if(!endDate || !endTime){
            Common.alert({msg:'请输入结束时间！',icon:Ext.MessageBox.WARNING});
            return false;
        }
        var startDateStr = Ext.util.Format.date(startDate,'Y-m-d')+' ' + Ext.util.Format.date(startTime,'H:i:s');
        var endDateStr = Ext.util.Format.date(endDate,'Y-m-d')+' ' + Ext.util.Format.date(endTime,'H:i:s');


        var nowTime = new Date();
        var checkStartTime = new Date(startDateStr.replace(/-/g,"/"));
        var checkEndTime = new Date(endDateStr.replace(/-/g,"/"));

        if (checkStartTime > checkEndTime) {
            Common.alert({msg:'开始日期不能晚于结束日期！',icon:Ext.MessageBox.WARNING});
            this.form.getForm().findField('startDate').setValue(null);
            this.form.getForm().findField('startTime').setValue(null);
            return false;
        }

        if (checkEndTime < nowTime) {
            Common.alert({msg:'结束时间不能是过往时间！',icon:Ext.MessageBox.WARNING});
            this.form.getForm().findField('endDate').setValue(null);
            return false;
        }
        return true;
    }

});